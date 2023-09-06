package cloud.cstream.chat.core.filters;

import cloud.cstream.chat.core.wrappers.RepeatableHttpServletRequest;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.Objects;

import static cn.hutool.extra.servlet.JakartaServletUtil.METHOD_GET;

public class RepeatableReadFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest
                && StrUtil.startWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)
                && !((HttpServletRequest) request).getMethod().equals(METHOD_GET)) {
            requestWrapper = new RepeatableHttpServletRequest((HttpServletRequest) request);
        }
        if (Objects.isNull(requestWrapper)) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化
    }

    @Override
    public void destroy() {
        // 销毁
    }
}
