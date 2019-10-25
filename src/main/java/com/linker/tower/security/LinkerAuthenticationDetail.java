package com.linker.tower.security;

import lombok.Data;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;


@Data
public class LinkerAuthenticationDetail extends WebAuthenticationDetails {

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 是否记住登录状态
     */
    private Boolean rememberMe;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 微信openId
     */
    private String openId;


    public LinkerAuthenticationDetail(HttpServletRequest request) {
        super(request);
    }

    public LinkerAuthenticationDetail(HttpServletRequest request, String loginName, Boolean rememberMe) {
        super(request);
        this.loginName = loginName;
        this.rememberMe = rememberMe;
    }

}
