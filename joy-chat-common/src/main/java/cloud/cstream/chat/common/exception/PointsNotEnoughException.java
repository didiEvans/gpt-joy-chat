package cloud.cstream.chat.common.exception;

import cloud.cstream.chat.common.domain.IResultCode;
import cloud.cstream.chat.common.domain.ResultCode;
import lombok.Getter;

/**
 * 积分不足异常
 */
public class PointsNotEnoughException extends RuntimeException {
    @Getter
    private final IResultCode resultCode;

    public PointsNotEnoughException(String message) {
        super(message);
        this.resultCode = ResultCode.ARRIVAL_LIMIT_ERROR;
    }
}
