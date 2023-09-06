package cloud.cstream.chat.common.util;

import com.google.common.collect.Maps;
import lombok.experimental.UtilityClass;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

import static cloud.cstream.chat.common.constants.CoreConstants.SIGN_SECRET_KEY;

@UtilityClass
public class SignUtil {


    public static String sign(Map<String, String> requestPram) {
        Map<String, String> sortedParams = new TreeMap<>(requestPram);
        StringBuilder sb = new StringBuilder();
        sortedParams.forEach((k, v) -> {
            // 拼接签名
            sb.append(k).append("=").append(v).append("&");
        });
        // 加入密钥
        sb.append("key=").append(SIGN_SECRET_KEY);
        // 使用MD5计算签名
        return md5(sb.toString());
    }


    public static String sign(String srcRequestParam) {
       String signContent = srcRequestParam + "key=" + SIGN_SECRET_KEY;
        // 使用MD5计算签名
        return md5(signContent);
    }


    public static void main(String[] args) {
//        String requestParam = "options={conversationId=0668cacc-3996-4653-a672-e4459c7c3c60, parentMessageId=0721772e-da97-4257-ab5f-7ed368d650bd}&prompt=你好&sysPromptLibId=8&";
////        String requestParam = "options={}&prompt=你好&key=ulqBVA0Z3tgwOt5e8leWkgZ6HvRxK5KF";
//        String s = md5(requestParam);
        Map<String, String> kvHashMap = Maps.newTreeMap();
        kvHashMap.put("picVerificationCode", "rv8imh");
        kvHashMap.put("picCodeSessionId", "6a004aa5-1ce5-454d-b2a6-28e462803d37");
        kvHashMap.put("phone","13060368314");
        String sign = sign(kvHashMap);
        System.out.println(sign);
    }


    /**
     * 对字符串进行 MD5 摘要计算
     *
     * @param str 待计算的字符串
     * @return MD5 值
     */
    private String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(str.getBytes());
            StringBuilder result = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(b & 0xFF);
                if (hex.length() == 1) {
                    result.append("0");
                }
                result.append(hex);
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


    public static boolean verify(String srcRequestParam, String requestSign) {
        String sign = sign(srcRequestParam);
        return sign.equals(requestSign);
    }
}
