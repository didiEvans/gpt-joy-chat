package cloud.cstream.chat.common.exception;

import cloud.cstream.chat.common.domain.IResultCode;
import cloud.cstream.chat.common.domain.ResultCode;
import lombok.Getter;

/**
 * @author Anker
 * @date 2023/3/23 00:28
 * 业务异常
 */
public class ServiceException extends RuntimeException {

    @Getter
    private final IResultCode resultCode;

    public ServiceException(String message) {
        super(message);
        this.resultCode = ResultCode.FAILURE;
    }
}
