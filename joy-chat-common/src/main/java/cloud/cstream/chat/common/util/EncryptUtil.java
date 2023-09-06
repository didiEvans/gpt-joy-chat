package cloud.cstream.chat.common.util;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.digest.MD5;
import cn.hutool.crypto.symmetric.AES;
import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;

import static cloud.cstream.chat.common.constants.CoreConstants.ENCRYPT_IV;
import static cloud.cstream.chat.common.constants.CoreConstants.ENCRYPT_K;

/**
 * 邀请码加解密工具类
 *
 * @author YBIn
 */
@UtilityClass
public class EncryptUtil {


    private static final AES AES = new AES(Mode.CBC, Padding.PKCS5Padding, ENCRYPT_K.getBytes(), ENCRYPT_IV.getBytes());

    public static String aesEncryptBase64(String content){
       return AES.encryptBase64(content);
    }

    public static String aesEncryptHex(String content){
        return AES.encryptHex(content);
    }

    public static String aesDecrypt(String content){
        return AES.decryptStr(content);
    }

    public static String md5Encrypt(String content, String salt){
         return MD5.create().digestHex16(content + salt, StandardCharsets.UTF_8);
    }

}
