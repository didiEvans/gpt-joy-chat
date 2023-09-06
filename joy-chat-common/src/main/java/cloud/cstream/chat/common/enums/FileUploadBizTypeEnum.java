package cloud.cstream.chat.common.enums;

/**
 * 文件上传业务类型
 *
 * @author evans
 * @description
 * @date 2023/6/21
 */

import cloud.cstream.chat.common.exception.ServiceException;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum FileUploadBizTypeEnum {


    /**
     * 客户端AI会话参考图
     * DRAW_ref/{date}/{uid}/{fileName}
     */
    CLIENT_AI_DRAW_REF_PIC(1,"draw_ref/{}/{}/{}", RootDirEnum.CLIENT),
    /**
     * 客户端AI会话响应结果图
     * DRAW_resp/{date}/{fileName}
     */
    CLIENT_AI_DRAW_RESP_PICTURE(1,"draw_resp/{}/{}", RootDirEnum.CLIENT),
    /**
     * 客户端用户头像
     * avatar/{date}/{uid}/{fileName}
     */
    CLIENT_USER_AVATAR(2,"avatar/{}/{}/{}", RootDirEnum.CLIENT),
    /**
     * 管理端广告banner
     * ad/{date}/{fileName}
     */
    ADMIN_AD_BANNER(3,"ad/{}/{}", RootDirEnum.ADMIN);


    private final Integer code;
    /**
     * 业务路径
     */
    private final String bizDirPattern;
    /**
     * 根目录地址
     */
    private final RootDirEnum rootDir;


    public static FileUploadBizTypeEnum load(Integer code){
        return Arrays.stream(values()).filter(el -> el.getCode().equals(code)).findAny().orElseThrow(() -> new ServiceException("文件服务类型未定义"));
    }

    public static String getDirPattern(Integer code){
        return load(code).getBizDirPattern();
    }

    public static String format(Integer code, Object ... params){
        String dirPattern = getDirPattern(code);
        return StrUtil.format(dirPattern, params);
    }

    public static String format(FileUploadBizTypeEnum typeEnum, Object ... params){
        String dirPattern = typeEnum.getBizDirPattern();
        return typeEnum.getRootDir().rootPath.concat(StrUtil.format(dirPattern, params));
    }

    @Getter
    @AllArgsConstructor
    enum RootDirEnum{
        /**
         * 客户端
         */
        CLIENT("client/"),
        /**
         * 管理端
         */
        ADMIN("admin/")
        ;

        private final String rootPath;

    }


}
