package com.example.food_corner_v2_java.interceptors;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.security.Principal;

@Component
public class LoggerInterceptor implements HandlerInterceptor {

    Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request,
                                @NotNull HttpServletResponse response, @NotNull Object object, Exception arg3)
            throws Exception {
        long startTime = System.currentTimeMillis();
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        int statusCode = response.getStatus();
        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;
        String logMessage = String.format("Completed: %s %s %d %dms", method, requestURI, statusCode, responseTime);
        log.info(logMessage);
    }

    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object object)
            throws Exception {
        log.info("Request processed successfully");
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response, @NotNull Object object) throws Exception {
        String token = request.getHeader("Authorization");
        log.info("Incoming request: {} {} {}", request.getMethod(), request.getRequestURI(),token);
        return true;
    }

}
