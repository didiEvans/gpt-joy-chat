package cloud.cstream.chat.common.exception;

import cloud.cstream.chat.common.domain.IResultCode;
import cloud.cstream.chat.common.domain.ResultCode;
import lombok.Getter;

/**
 * @author evans
 * @description
 * @date 2023/6/3
 */
public class IllegalApiAccessException extends RuntimeException {

    @Getter
    private final IResultCode resultCode;

    public IllegalApiAccessException(String message) {
        super(message);
        this.resultCode = ResultCode.ARRIVAL_LIMIT_ERROR;
    }
}
