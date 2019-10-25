package com.linker.tower.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AjaxAccessDeniedHandler implements AccessDeniedHandler {

    public static final String UNAUTHORIZED_MESSAGE = "Access failed";

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("AjaxAccessDeniedHandler, request={}", request.getParameterMap());
        response.sendError(HttpServletResponse.SC_FORBIDDEN, UNAUTHORIZED_MESSAGE);
    }
}
