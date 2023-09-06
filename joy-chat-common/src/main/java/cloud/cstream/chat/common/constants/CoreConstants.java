package cloud.cstream.chat.common.constants;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * tips
 *
 * @author YBin
 * @date 2023/4/25 16:49
 */
public interface CoreConstants {


    String EMAIL_AUTH_CONTENT = "<div>\n" +
            "  <p style='margin-bottom: 10px;font-size: 18px;'>点击链接完成【悦聊】账号的注册:</p>\n" +
            "  <a style='color:#18a058 ;font-size: 18px;' href={}> 点此进行验证 </a>\n" +
            "  <div style='background: rgba(24, 160, 88, 0.1);color: #18a058;padding: 18px;font-size: 16px;width: 400px;border-radius: 8px;text-align: center;margin-top: 60px;'>\n" +
            "    智享聊天乐趣，尽在悦聊\n" +
            "  </div>\n" +
            "</div>\n";


    String ENCRYPT_K = "0ivfcmgytnlrtc4l";


    String ENCRYPT_IV = "epcedx5jvh4fqn2y";
    String LIMIT_1 = "LIMIT 1";


    Integer INIT_CHAT_COUNT = 10;
    String JC_FIX = "jc";
    Integer LIMIT_SEND_CAPTCHA_COUNT = 3;
    String SIGN_SECRET_KEY = "ulqBVA0Z3tgwOt5e8leWkgZ6HvRxK5KF";
    String TIMESTAMP = "timestamp";
    long MAX_REQUEST_DIFF_TIME = 60 * 1000;
    String GET = "GET";
    String SIGN = "sign";
    String CONTENT_TYPE = "Content-Type";
    Integer DEFAULT_TIME_OUT = 5000;
    int Draw_TASK_QUEUE = 200;
    String PHONE_EXISTS_SALT = "rxk5kf";
    /**
     * 测试验证码
     */
    String TEST_CAPTCHA = "9999";
    String DEV_ENV = "dev";
    int TOKEN_COOL_VALUE = 20;
    int MIN_CONTEXT_SIZE = 10;
    /**
     * 回答问题时, 尽可能详细,始终保持理性和中立态度
     */
    String DEFAULT_SYS_PROMPT = "When answering questions, be as detailed as possible and always be rational and neutral";
    /**
     * 默认告警邮箱地址
     */
    List<String> DEFAULT_WARRING_EMAIL = Lists.newArrayList("17781696032@163.com", "evans6183@163.com");
}
