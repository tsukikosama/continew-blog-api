package top.continew.admin.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 打印请求URL
        log.info("请求URL:{}",request.getRequestURL());

        // 打印请求方法(GET, POST等)
        log.info("请求方法{}",request.getMethod());

        // 打印请求参数
        Enumeration<String> parameterNames = request.getParameterNames();
        List<String> params = Collections.list(parameterNames).stream()
                .map(name -> name + "=" + request.getParameter(name))
                .collect(Collectors.toList());
        log.info("请求参数: {}", String.join(",", params));

        // 返回true表示继续流程，false会中断请求
        return true;
    }
}
