package cloud.cstream.chat.common.exception;

import cloud.cstream.chat.common.domain.IResultCode;
import cloud.cstream.chat.common.domain.ResultCode;
import lombok.Getter;

/**
 * @author Anker
 * @date 2023/3/23 12:49
 * 鉴权异常
 */
public class AuthException extends RuntimeException {

    @Getter
    private final IResultCode resultCode;

    public AuthException(String message) {
        super(message);
        this.resultCode = ResultCode.UN_AUTHORIZED;
    }
}
