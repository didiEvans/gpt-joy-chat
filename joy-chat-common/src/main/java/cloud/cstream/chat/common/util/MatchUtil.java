package cloud.cstream.chat.common.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符匹配工具类
 *
 * @author Anker
 */
@UtilityClass

public class MatchUtil {


    /**
     * 是否包含除字母/ 数字/ 汉子 以外的任意字符
     *
     * @param content
     * @return
     */
    public static boolean containSpecialChar(String content){
        // 匹配除字母、数字和汉字以外的任意字符
        String regex = "[^a-zA-Z0-9\\u4E00-\\u9FA5]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        return matcher.find();
    }



}
