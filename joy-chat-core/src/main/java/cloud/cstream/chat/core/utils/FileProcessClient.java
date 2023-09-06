package cloud.cstream.chat.core.utils;

import cloud.cstream.chat.common.exception.ServiceException;
import cloud.cstream.chat.core.config.CosConfigProperties;
import cloud.cstream.chat.core.domain.pojo.CosFileResourcePair;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import com.google.common.collect.Lists;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.Headers;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.utils.DateUtils;
import com.qcloud.cos.utils.IOUtils;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Credentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import static cloud.cstream.chat.common.constants.StrConstants.*;


/**
 * Oss工具类
 *
 * @author Anker
 */
@Slf4j
@Component
public class FileProcessClient {

    @Autowired
    private CosConfigProperties cosConfigProperties;


    public void cosUpload(String filePath, MultipartFile file) {
        // 获取临时凭证
        Credentials credentials = this.getTmpSecret();
        // 1 初始化用户身份信息(secretId, secretKey)
        COSCredentials cred = new BasicCOSCredentials(credentials.tmpSecretId, credentials.tmpSecretKey);
        // 2 设置 bucket 区域,详情请参见 COS 地域 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region(cosConfigProperties.getRegion()));
        // 3 生成 cos 客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        // bucket 名需包含 appid
        String bucketName = cosConfigProperties.getBucketName();
        // 设置 x-cos-security-token header 字段
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setSecurityToken(credentials.sessionToken);
        try {
            // 创建请求
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filePath, file.getInputStream(), objectMetadata);
            PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
            // 成功：putobjectResult 会返回文件的 etag
            String etag = putObjectResult.getETag();
        } catch (Exception e) {
            log.error("文件上传异常:", e);
            throw new ServiceException("上传失败");
        }
        // 关闭客户端
        cosclient.shutdown();
    }


    private Credentials getTmpSecret() {
        TreeMap<String, Object> config = new TreeMap<String, Object>();
        try {
            // 密钥 SecretId
            config.put("secretId", cosConfigProperties.getSecretId());
            //  密钥 SecretKey
            config.put("secretKey", cosConfigProperties.getSecretKey());
            // 临时密钥有效时长，单位是秒，默认 1800 秒，目前主账号最长 2 小时（即 7200 秒），子账号最长 36 小时（即 129600）秒
            config.put("durationSeconds", 7200);
            // 换成您的 bucket
            config.put("bucket", cosConfigProperties.getBucketName());
            // 换成 bucket 所在地区
            config.put("region", cosConfigProperties.getRegion());
            // 这里改成允许的路径前缀，可以根据自己网站的用户登录态判断允许上传的具体路径
            config.put("allowPrefixes", new String[]{
                    "client/*",
                    "admin/*"
            });
            // 密钥的权限列表。必须在这里指定本次临时密钥所需要的权限。
            // 简单上传、表单上传和分块上传需要以下的权限，其他权限列表请参见 https://cloud.tencent.com/document/product/436/31923
            String[] allowActions = new String[]{
                    // 简单上传
                    "name/cos:PutObject",
                    // 表单上传、小程序上传
                    "name/cos:PostObject",
                    // 下载对象
                    "name/cos:GetObject"
            };
            config.put("allowActions", allowActions);
            return CosStsClient.getCredential(config).credentials;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("no valid secret !");
        }
    }

    /**
     * 获取资源地址
     *
     * @param key 文件标识
     * @return
     */
    public String getResource(String key) {
        // 详细代码参见本页：简单操作 -> 创建 COSClient
        COSClient cosClient = createCOSClient();
        try {
            return getAccessFileUrl(key, cosClient);
            // 确认本进程不再使用 cosClient 实例之后，关闭即可
        } catch (Exception e) {
            log.error("获取稳健对象异常:", e);
            throw new ServiceException("文件获取异常");
        } finally {
            cosClient.shutdown();
        }
    }

    private String getAccessFileUrl(String key, COSClient cosClient) {
        // 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
        String bucketName = cosConfigProperties.getBucketName();
        // 对象键(Key)是对象在存储桶中的唯一标识。详情请参见 [对象键](https://cloud.tencent.com/document/product/436/13324)
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET);
        // 设置下载时返回的 http 头
        ResponseHeaderOverrides responseHeaders = new ResponseHeaderOverrides();
        // 设置返回头部里包含文件名信息
        String contentDisposition = "filename=".concat(UUID.randomUUID().toString());
        String cacheExpireStr = DateUtils.formatRFC822Date(new Date(System.currentTimeMillis() + 24L * 3600L * 1000L));
        responseHeaders.setContentType(IMG_CONTENT_TYPE);
        responseHeaders.setContentLanguage(ZH_CN);
        responseHeaders.setContentDisposition(contentDisposition);
        responseHeaders.setCacheControl(NO_CACHE);
        responseHeaders.setExpires(cacheExpireStr);
        req.setResponseHeaders(responseHeaders);
        // 设置签名过期时间(可选)，若未进行设置，则默认使用 ClientConfig 中的签名过期时间(1小时) 这里设置签名在1个小时后过期
        Date expirationDate = new Date(System.currentTimeMillis() + 60L * 60L * 1000L);
        req.setExpiration(expirationDate);
        // 填写本次请求的参数
        req.putCustomRequestHeader(Headers.HOST, cosClient.getClientConfig().getEndpointBuilder().buildGeneralApiEndpoint(bucketName));
        URL url = cosClient.generatePresignedUrl(req);
        return url.toString();
    }

    /**
     * 创建COS客户端
     *
     * @return
     */
    private COSClient createCOSClient() {
        // 这里需要已经获取到临时密钥的结果。
        // 临时密钥的生成参见 https://cloud.tencent.com/document/product/436/14048#cos-sts-sdk
        Credentials tmpSecret = this.getTmpSecret();
        String tmpSecretId = tmpSecret.tmpSecretId;
        String tmpSecretKey = tmpSecret.tmpSecretKey;
        String sessionToken = tmpSecret.sessionToken;
        COSCredentials cred = new BasicSessionCredentials(tmpSecretId, tmpSecretKey, sessionToken);
        // ClientConfig 中包含了后续请求 COS 的客户端设置：
        ClientConfig clientConfig = new ClientConfig();
        // 设置 bucket 的地域
        clientConfig.setRegion(new Region(cosConfigProperties.getRegion()));
        // 设置请求协议, http 或者 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 设置 socket 读取超时，默认 30s
        clientConfig.setSocketTimeout(30 * 1000);
        // 设置建立连接超时，默认 30s
        clientConfig.setConnectionTimeout(30 * 1000);
        // 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

    public List<CosFileResourcePair> batchGetResource(List<CosFileResourcePair> pairs) {
        if (CollUtil.isEmpty(pairs)) {
            return pairs;
        }
        // 创建 COSClient
        COSClient cosClient = createCOSClient();
        try {
            for (CosFileResourcePair pair : pairs) {
                if (CollUtil.isEmpty(pair.getFileKeys())){
                    continue;
                }
                List<String> fileAddress = Lists.newArrayList();
                for (String fileKey : pair.getFileKeys()) {
                    String fileUrl = this.getResource(fileKey);
                    fileAddress.add(fileUrl);
                }
                pair.setFileAddress(fileAddress);
            }
        } catch (Exception e) {
            log.error("获取稳健对象异常:", e);
            throw new ServiceException("文件获取异常");
        } finally {
            cosClient.shutdown();
        }
        return pairs;
    }

    /**
     * 根据地址获得数据的输入流
     *
     * @param strUrl 网络连接地址
     * @return url的输入流
     */
    public InputStream getInputStreamByUrl(String strUrl) {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(strUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20 * 1000);
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            IOUtils.copy(conn.getInputStream(), output);
            return new ByteArrayInputStream(output.toByteArray());
        } catch (Exception e) {
            log.error("根据地址获得数据的输入流异常 Exception，", e);
        } finally {
            try {
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                log.error("断开输入流异常 Exception，", e);
            }
        }
        return null;
    }

    public MultipartFile convertToMultipartFile(InputStream inputStream, String fileName) throws IOException {
        return new MockMultipartFile(fileName, fileName,"", inputStream);
    }
}
