package com.linker.tower.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wang.gs
 */
@Slf4j
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {
    public static final String UNAUTHORIZED_MESSAGE = "Access failed";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("AjaxAuthenticationEntryPoint, request={}", request.getParameterMap());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, UNAUTHORIZED_MESSAGE);
    }
}
