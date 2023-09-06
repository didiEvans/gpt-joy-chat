package cloud.cstream.chat.core.config;

import cloud.cstream.chat.common.domain.R;
import cloud.cstream.chat.common.domain.ResultCode;
import cloud.cstream.chat.common.exception.*;
import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author Anker
 * @date 2023/3/23 10:55
 * 异常处理器
 */
@Slf4j
@Configuration
@RestControllerAdvice
public class GlobalExceptionHandlerConfig {

    /**
     * Sa-token 鉴权拦截
     * HTTP 状态为 401
     *
     * @param e 异常信息
     * @return 返回值
     */
    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R<Void> handleError(NotLoginException e) {
        log.warn("鉴权拦截", e);
        return R.fail(ResultCode.UN_AUTHORIZED, "请先登录!!");
    }

    /**
     * 业务异常处理
     * HTTP 状态为 400
     *
     * @param e 异常信息
     * @return 返回值
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleError(ServiceException e) {
        log.error("业务异常", e);
        return R.fail(e.getResultCode(), e.getMessage());
    }

    /**
     * 鉴权异常处理
     * HTTP 状态为 401
     *
     * @param e 异常信息
     * @return 返回值
     */
    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public R<Void> handleError(AuthException e) {
        log.error("鉴权异常", e);
        return R.fail(e.getResultCode(), e.getMessage());
    }

    /**
     * 自定义校验规则异常
     * HTTP 状态为 400
     *
     * @param e 异常信息
     * @return 返回值
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleError(MethodArgumentNotValidException e) {
        log.warn("PostBody校验不通过", e);
        return wrapErrors(e.getAllErrors());
    }

    /**
     * 其他异常处理
     * HTTP 状态为 500
     *
     * @param e 异常信息
     * @return 返回值
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<Void> handleError(Throwable e) {
        log.error("服务器异常", e);
        return R.fail(ResultCode.INTERNAL_SERVER_ERROR, ResultCode.INTERNAL_SERVER_ERROR.getMessage());
    }


    /**
     * Sa-token 鉴权拦截
     * HTTP 状态为 200 特殊处理, 前端要做跳转
     *
     * @param e 异常信息
     * @return 返回值
     */
    @ExceptionHandler(ArrivalLimitException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<Void> handleError(ArrivalLimitException e) {
        String msg = ResultCode.ARRIVAL_LIMIT_ERROR.getMessage();
        if (CharSequenceUtil.isNotBlank(e.getMessage())) {
            msg = e.getMessage();
        }
        log.warn("鉴权拦截", e);
        return R.fail(ResultCode.ARRIVAL_LIMIT_ERROR, msg);
    }


    /**
     * Sa-token 鉴权拦截
     * HTTP 状态为 200 特殊处理, 前端要做跳转
     *
     * @param e 异常信息
     * @return 返回值
     */
    @ExceptionHandler(IllegalApiAccessException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<Void> handleError(IllegalApiAccessException e) {
        String msg = e.getMessage();
        if (CharSequenceUtil.isBlank(e.getMessage())) {
            msg = ResultCode.ILLEGAL_ACCESS.getMessage();
        }
        log.warn("访问异常", e);
        return R.fail(ResultCode.ARRIVAL_LIMIT_ERROR, msg);
    }



    /**
     * Sa-token 鉴权拦截
     * HTTP 状态为 200 特殊处理, 前端要做跳转
     *
     * @param e 异常信息
     * @return 返回值
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<Void> handleError(IllegalArgumentException e) {
        String msg = e.getMessage();
        if (CharSequenceUtil.isBlank(msg)) {
            msg = ResultCode.INTERNAL_SERVER_ERROR.getMessage();
        }
        log.warn("参数异常", e);
        return R.fail(msg);
    }


    /**
     * Sa-token 鉴权拦截
     * HTTP 状态为 200 特殊处理, 前端要做跳转
     *
     * @param e 异常信息
     * @return 返回值
     */
    @ExceptionHandler(PointsNotEnoughException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<Void> handleError(PointsNotEnoughException e) {
        return R.fail(e.getResultCode(), e.getMessage());
    }

    /**
     * 封装validator的错误信息列表
     *
     * @param errors 错误信息列表
     */
    private R<Void> wrapErrors(List<ObjectError> errors) {
        if (CollectionUtil.isEmpty(errors)) {
            return R.fail(ResultCode.FAILURE, ResultCode.FAILURE.getMessage());
        }
        StringBuilder sb = new StringBuilder();
        // 自定义校验，error类型为 ViolationObjectError
        errors.forEach(error -> sb.append(error.getDefaultMessage()));

        return R.fail(ResultCode.FAILURE, sb.toString());
    }

}
