package cloud.cstream.chat.common.constants;

/**
 * @author evans
 * @description
 * @date 2023/5/9
 */
public interface RedisKeyConstant {

    String PROJECT = "joy_chat:";

    String MODULE_CHAT = PROJECT.concat("module_chat:");
    String MODULE_DRAW = PROJECT.concat("module_draw:");

    String CORE = MODULE_CHAT.concat("core:");
    /**
     * 用户免费会话数缓存
     * %s userId
     */
    String USER_FREE_SESSION_COUNT_CACHE = MODULE_CHAT.concat("user_free_session_count_cache:%s_%s");


    /**
     * 用户增值服务会话上限缓存
     * %s userId
     */
    String USER_VAS_SESSION_LIMIT_CACHE = MODULE_CHAT.concat("user_vas_session_limit_cache:%s");
    /**
     * 百度文本审核accessToken
     */
    String BD_TEXT_REVIEW_APP_TOKEN_CACHE = MODULE_CHAT.concat("bd_text_review_app_token_cache");
    /**
     * 用户手机登录注册验证码
     */
    String USER_LOGIN_BY_PHONE_VERIFY_CODE = MODULE_CHAT.concat("user_login_by_phone_verify_code:%s");
    /**
     * 短信验证码发送次数缓存
     */
    String USER_SEND_CAPTCHA_LIMIT = MODULE_CHAT.concat("user_send_captcha_limit:%s_%s");
    /**
     * 短信配置缓存
     */
    String SYS_SMS_CONFIG = MODULE_CHAT.concat("sys_sms_config:%s");
    /**
     * chatGPT配置缓存
     */
    String JY_AI_DRAW_STYLE_SELECTOR = MODULE_DRAW.concat("jy_ai_draw_style_selector");
    String USER_HIT_SENSITIVE_WORD_COUNT = "user_hit_sensitive_word_count:%s";
    CharSequence USER_SINGLE_DRAW_TASK = MODULE_DRAW.concat("user_single_draw_task:{}");

    String APP_UPGRADE_CONF = PROJECT.concat("app_upgrade_conf");
}
