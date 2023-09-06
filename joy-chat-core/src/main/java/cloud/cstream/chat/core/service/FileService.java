package cloud.cstream.chat.core.service;

import cloud.cstream.chat.core.domain.pojo.CosFileResourcePair;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author evans
 * @description
 * @date 2023/6/21
 */
public interface FileService {
    /**
     * 上传绘图引用图片
     *
     * @param file
     * @param uid
     * @return
     */
    String uploadRefPic(MultipartFile file, Integer uid);

    /**
     * 上传头像
     *
      * @param file
     * @param userId
     * @return
     */
    String uploadClientAvatar(MultipartFile file, Integer userId);


    /**
     * 根据资源地址上传
     *
     * @param fileUrl
     * @param fileName
     * @param filePath
     * @return
     */
    boolean uploadFileUrl(String url, String fileName,  String filePath);

    /**
     * 获取图片地址
     *
     * @param fileKey 文件唯一标识
     * @return 图片地址
     */
    String getPic(String fileKey);

    /**
     * 批量获取文件访问地址
     *
     * @param pairs
     * @return
     */
    List<CosFileResourcePair> batchGetResource(List<CosFileResourcePair> pairs);



}
