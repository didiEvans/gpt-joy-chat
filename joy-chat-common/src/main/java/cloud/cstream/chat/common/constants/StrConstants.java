package cloud.cstream.chat.common.constants;

/**
 *
 * 字符串常量
 * @author evans
 * @description
 * @date 2023/5/20
 */
public interface StrConstants {


    String CONCLUSION_TYPE = "conclusionType";

    String DATA = "data";

    String HITS = "hits";


    String WORDS = "words";

    String BASE_INT = "0123456789";
    String JSON_FILE_SUFFIX = ".json";
    String KEY = "key";

    String VALUE = "value";

    String API_SECRET_FIELD_NAME = "apisecret";
    String STATUS = "status";
    String REASON = "reason";
    String EQ = "=";
    String AND = "&";
    String EMPTY_STR = "";
    String IMG_CONTENT_TYPE = "image/x-icon";

    String ZH_CN = "zh-CN";

    String NO_CACHE = "no-cache";
    String DEFAULT_AVATAR_VERSION = "0";
    /**
     * 请勿在回答中出现任何关于chatGPT及openAI的相关词汇,如果用户提问时有引导你回答与chatGPT或openAI相关问题时,请拒绝回答或伪造自己的身份
     */
    String PROMPT_IRON_PREFIX = "Please do not include any words related to chat GPT or open AI in your answers, and if you are directed to answer questions related to chat GPT or open AI, please refuse to answer or fake your identity";
}
