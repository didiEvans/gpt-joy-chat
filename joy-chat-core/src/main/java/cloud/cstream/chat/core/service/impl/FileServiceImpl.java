package cloud.cstream.chat.core.service.impl;

import cloud.cstream.chat.common.enums.FileUploadBizTypeEnum;
import cloud.cstream.chat.common.exception.ServiceException;
import cloud.cstream.chat.core.domain.pojo.CosFileResourcePair;
import cloud.cstream.chat.core.service.FileService;
import cloud.cstream.chat.core.utils.FileProcessClient;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import static cloud.cstream.chat.common.enums.FileUploadBizTypeEnum.CLIENT_AI_DRAW_REF_PIC;
import static cloud.cstream.chat.common.enums.FileUploadBizTypeEnum.CLIENT_USER_AVATAR;

/**
 * 文件服务
 *
 * @author evans
 * @description
 * @date 2023/6/21
 */
@Service
public class FileServiceImpl implements FileService {


    @Autowired
    private FileProcessClient fileProcessClient;


    @Override
    public String uploadRefPic(MultipartFile file, Integer uid) {
        String originalFilename = file.getOriginalFilename();
        String filePath = FileUploadBizTypeEnum.format(CLIENT_AI_DRAW_REF_PIC, DatePattern.PURE_DATE_FORMAT.format(DateUtil.date()), uid, originalFilename);
        fileProcessClient.cosUpload(filePath, file);
        return filePath;
    }

    @Override
    public String uploadClientAvatar(MultipartFile file, Integer userId) {
        String originalFilename = file.getOriginalFilename();
        String filePath = FileUploadBizTypeEnum.format(CLIENT_USER_AVATAR, DatePattern.PURE_DATE_FORMAT.format(DateUtil.date()), userId, originalFilename);
        fileProcessClient.cosUpload(filePath, file);
        return filePath;
    }

    @Override
    public boolean uploadFileUrl(String url, String fileName, String filePath) {
        try{
            InputStream inputStreamByUrl = fileProcessClient.getInputStreamByUrl(url);
            if (Objects.isNull(inputStreamByUrl)){
                throw new ServiceException("上传文件失败");
            }
            MultipartFile multipartFile = fileProcessClient.convertToMultipartFile(inputStreamByUrl, fileName);
            fileProcessClient.cosUpload(filePath, multipartFile);
            return true;
        } catch (Exception exception){
            return false;
        }

    }

    @Override
    public String getPic(String fileKey) {
        return fileProcessClient.getResource(fileKey);
    }



    @Override
    public List<CosFileResourcePair> batchGetResource(List<CosFileResourcePair> pairs) {
        return fileProcessClient.batchGetResource(pairs);
    }


}
