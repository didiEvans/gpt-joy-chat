package cloud.cstream.chat.core.strategy.sms.impl;

import cloud.cstream.chat.common.enums.SmsSendHandlerEnum;
import cloud.cstream.chat.core.domain.entity.SysSmsConfigDO;
import cloud.cstream.chat.core.strategy.sms.AbstractSendSmsTypeStrategy;
import cn.hutool.json.JSONUtil;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author evans
 * @description
 * @date 2023/5/28
 */
@Slf4j
@Service
public class TencentSmsSendHandlerImpl extends AbstractSendSmsTypeStrategy {





    @Override
    public boolean sendCaptcha(String phone, String captcha) {
        SysSmsConfigDO smsConfig = super.getSmsConfig(SmsSendHandlerEnum.TENCENT);
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户 SecretId 和 SecretKey，此处还需注意密钥对的保密
            // 代码泄露可能会导致 SecretId 和 SecretKey 泄露，并威胁账号下所有资源的安全性。以下代码示例仅供参考，建议采用更安全的方式来使用密钥，请参见：https://cloud.tencent.com/document/product/1278/85305
            // 密钥可前往官网控制台 https://console.cloud.tencent.com/cam/capi 进行获取
            Credential cred = new Credential(smsConfig.getSecretId(), smsConfig.getSecretKey());
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(smsConfig.getEndpoint());
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            req.setSmsSdkAppId(smsConfig.getSdkAppId());
            req.setPhoneNumberSet(new String[]{phone});
            req.setSignName(smsConfig.getSign());
            req.setTemplateId(smsConfig.getCaptchaTid());
            req.setTemplateParamSet(new String[]{captcha});
            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);
            // 输出json格式的字符串回包
            log.error("腾讯云短信验证码发送结果:{}", JSONUtil.toJsonStr(resp));
        } catch (TencentCloudSDKException e) {
            log.error("腾讯云短信发送验证码异常:", e);
            return false;
        }
        return true;
    }
}
