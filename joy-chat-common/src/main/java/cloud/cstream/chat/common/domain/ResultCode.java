package cloud.cstream.chat.common.domain;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Anker
 * @date 2023/3/23 12:34
 * 结果状态码
 */
@Getter
@AllArgsConstructor
public enum ResultCode implements IResultCode {

    /**
     * 操作成功
     */
    SUCCESS(HttpServletResponse.SC_OK, "操作成功"),

    /**
     * 业务异常
     */
    FAILURE(HttpServletResponse.SC_BAD_REQUEST, "业务异常"),

    /**
     * 请求未授权
     */
    UN_AUTHORIZED(HttpServletResponse.SC_UNAUTHORIZED, "请求未授权"),

    /**
     * 服务器异常
     */
    INTERNAL_SERVER_ERROR(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "服务器异常"),
    /**
     * 到达会话次数限制
     */
    ARRIVAL_LIMIT_ERROR(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE, "今日会话额度已用完,购买增值套餐可享受不限次对话"),
    /**
     *
     */
    POINT_NOT_ENOUGH_ERROR(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE,"绘图积分不足,请购买积分后再试"),
    /**
     * 到达会话次数限制
     */
    ILLEGAL_ACCESS(HttpServletResponse.SC_BAD_REQUEST, "非法访问"),
    ;


    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;
}
