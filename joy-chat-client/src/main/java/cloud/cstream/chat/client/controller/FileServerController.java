package cloud.cstream.chat.client.controller;

import cloud.cstream.chat.client.utils.ClientUserLoginUtil;
import cloud.cstream.chat.common.domain.R;
import cloud.cstream.chat.core.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * client-文件相关操作
 *
 * @author evans
 * @description
 * @date 2023/6/21
 */
@RestController
@RequestMapping("client/file_server")
public class FileServerController {


    @Autowired
    private FileService fileService;


    /**
     * 上传ai绘画参考图
     *
     * @param file  文件
     * @return 文件地址
     */
    @PostMapping("upload_ref_pic")
    public R<String> uploadRefPic(@RequestParam("file")MultipartFile file){
        return R.data(fileService.uploadRefPic(file, ClientUserLoginUtil.getUserId()));
    }





}
