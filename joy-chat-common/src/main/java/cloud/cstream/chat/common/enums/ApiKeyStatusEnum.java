package cloud.cstream.chat.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * apikey 状态枚举
 */
@Getter
@AllArgsConstructor
public enum ApiKeyStatusEnum {

    /**
     * 账号被封了
     */
    ACCOUNT_DEACTIVATED("account_deactivated"),
    /**
     * key不正确
     */
    INVALID_API_KEY("invalid_api_key");


    private final String status;

    /**
     * api key 是否异常
     *
     * @param errorCode 错误码
     * @return true 异常/ false
     */
    public static boolean apiKeyIsException(String errorCode) {
        return Objects.nonNull(Arrays.stream(values()).filter(el -> el.getStatus().equals(errorCode)).findAny().orElse(null));
    }
}
