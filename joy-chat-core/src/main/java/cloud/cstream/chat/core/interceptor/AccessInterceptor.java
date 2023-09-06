package cloud.cstream.chat.core.interceptor;

import cloud.cstream.chat.common.constants.StrConstants;
import cloud.cstream.chat.common.enums.BoolEnum;
import cloud.cstream.chat.common.exception.IllegalApiAccessException;
import cloud.cstream.chat.common.exception.ServiceException;
import cloud.cstream.chat.common.util.ObjectMapperUtil;
import cloud.cstream.chat.common.util.SignUtil;
import cloud.cstream.chat.core.domain.pojo.ShutdownAppConf;
import cloud.cstream.chat.core.service.SysParamService;
import cloud.cstream.chat.core.wrappers.RepeatableHttpServletRequest;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static cloud.cstream.chat.common.constants.CoreConstants.*;

/**
 * @author evans
 * @description
 * @date 2023/6/3
 */
@Slf4j
@Component
public class AccessInterceptor implements HandlerInterceptor {



    // 无需校验时间戳的接口地址
    private final static List<String> NOT_VERIFY_TIMESTAMP_URI = Lists.newArrayList(
            "/client/user/send"
    );

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
//        // 校验时间戳
//        this.verifyTimestamp(request);
        // 签名校验
        return verifyRequestSign(request);
    }

    private boolean verifyRequestSign(HttpServletRequest request) throws IOException {
        String method = request.getMethod();
        if (HttpMethod.GET.name().equalsIgnoreCase(method) || HttpMethod.OPTIONS.name().equalsIgnoreCase(method)) {
            return true;
        }
        String contentType = request.getHeader(CONTENT_TYPE);
        if (CharSequenceUtil.isBlank(contentType)){
            return true;
        }
        if (contentType.contains(ContentType.MULTIPART.getValue())){
            return true;
        }
        String srcRequestParam = null;
        if (contentType.contains(ContentType.FORM_URLENCODED.getValue())) {
            srcRequestParam = this.getParamSortStr(request.getParameterMap());
        } else if (contentType.contains(ContentType.JSON.getValue())) {
            srcRequestParam = this.getJsonParamSortStr(request);
        }
        String requestSign = request.getHeader(SIGN);
        if (CharSequenceUtil.isBlankOrUndefined(requestSign)) {
            throw new IllegalApiAccessException("签名异常");
        }
        // 验证签名
        if (!SignUtil.verify(srcRequestParam, requestSign)){
            // 签名不匹配，请求非法
            throw new IllegalApiAccessException("签名异常");
        }
        return true;
    }

    private String getJsonParamSortStr(HttpServletRequest request) throws IOException {
        StringBuilder strBuilder = StrUtil.builder();
        String bodyStr = null;
        if (request instanceof RepeatableHttpServletRequest repeatableRequest) {
            bodyStr = repeatableRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        }
        if (CharSequenceUtil.isBlankOrUndefined(bodyStr)) {
            return null;
        }
        if (!JSONUtil.isTypeJSON(bodyStr)) {
            return null;
        }
        Map<String, Object> objectMap = ObjectMapperUtil.toMap(bodyStr, new TypeReference<TreeMap<String, Object>>() {});
        objectMap.forEach((k , v) -> {
            if (Objects.nonNull(v) && !v.equals(StrConstants.EMPTY_STR)){
                if (!ObjectUtil.isBasicType(v)){
                    v = JSONUtil.toJsonStr(v);
                }
                strBuilder.append(k).append(StrConstants.EQ).append(v).append(StrConstants.AND);
            }
        });
        return strBuilder.toString();
    }



    private Map<String, String> getFormRequestParam(Map<String, String[]> parameterMap) {
        Map<String, String> params = new HashMap<>(16);
        parameterMap.forEach((k, v) -> {
            params.put(k, v[0]);
        });
        return params;
    }


    private String getParamSortStr(Map<String, String[]> parameterMap) {
        StringBuilder strBuilder = StrUtil.builder();
        if (CollUtil.isEmpty(parameterMap)){
            return null;
        }
        parameterMap.forEach((k, v) -> {
            if (Objects.nonNull(v) && v.length > 0){
                strBuilder.append(k).append(StrConstants.EQ).append(v[0]).append(StrConstants.AND);
            }
        });
        return strBuilder.toString();
    }

    /**
     * 校验时间戳
     *
     * @param request
     */
    private void verifyTimestamp(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (this.shouldNotVerify(uri)){
            return;
        }
        String timestamp = request.getHeader(TIMESTAMP);
        if (CharSequenceUtil.isBlankOrUndefined(timestamp)) {
            log.warn("[{}] >>> 时间戳校验失败", uri);
            throw new IllegalApiAccessException("非法访问");
        }
        // 校验时间戳
        long serverTime = System.currentTimeMillis();
        // 请求间隔
        double diffTime = NumberUtil.sub(serverTime, Long.parseLong(timestamp));
        if (NumberUtil.compare(diffTime, MAX_REQUEST_DIFF_TIME) >= 0) {
            log.warn("[{}] >> 时间戳超时访问", uri);
            throw new IllegalApiAccessException("非法访问");
        }
    }

    private boolean shouldNotVerify(String requestURI) {
        return NOT_VERIFY_TIMESTAMP_URI.contains(requestURI);
    }
}
