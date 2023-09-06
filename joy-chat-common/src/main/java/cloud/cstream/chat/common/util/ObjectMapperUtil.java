package cloud.cstream.chat.common.util;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author Anker
 * @date 2023/3/24 17:50
 * jackson ObjectMapper 工具类
 */
@Slf4j
@UtilityClass
public class ObjectMapperUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将一个 Java 对象转换为 JSON 字符串
     *
     * @param object 待转换的对象
     * @return 转换后的 JSON 字符串
     */
    public static String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }

    /**
     * 将一个 JSON 字符串转换为 Java 对象
     *
     * @param json  待转换的 JSON 字符串
     * @return 转换后的 map对象
     */
    public static <K, V> Map<K, V> toMap(String json, TypeReference<? extends Map<K, V>> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(StrUtil.format("{} Failed to convert JSON to object", json, e));
        }
    }


    /**
     * 将一个 JSON 字符串转换为 Map对象
     *
     * @param json  待转换的 JSON 字符串
     * @param clazz 转换后的 Java 类型
     * @param <T>   Java 类型的泛型参数
     * @return 转换后的 Java 对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(StrUtil.format("{} Failed to convert JSON to object", json, e));
        }
    }
}
