package com.linker.tower.security;

import com.linker.tower.web.rest.errors.UnLoginException;
import com.linker.tower.web.rest.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * @author wang.gs
 */
@Slf4j
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Get the loginName of the current user.
     *
     * @return the loginName of the current user
     */
    public static Optional<String> getCurrentUserLoginName() {
        Optional<String> currentUser;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        currentUser = Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (authentication.getPrincipal() instanceof UserDetails) {
                        UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                        return springSecurityUser.getUsername();
                    } else if (authentication.getPrincipal() instanceof String) {
                        return (String) authentication.getPrincipal();
                    }
                    return null;
                });
        return currentUser;
    }

    public static LinkerAuthenticationDetail getAuthenticationDetail() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Optional<Object> detail = Optional.ofNullable(securityContext)
                .map(ctx -> ctx.getAuthentication())
                .map(authentication -> authentication.getDetails());
        if (detail.isPresent() && detail.get() instanceof LinkerAuthenticationDetail) {
            return (LinkerAuthenticationDetail) detail.get();
        }
        throw new UnLoginException();
    }

    public static Long getLoginUserId() {
        Long userId = getAuthenticationDetail().getUserId();
        if (!NumberUtil.isPositive(userId)) {
            throw new UnLoginException();
        }
        return userId;
    }

    public static boolean isLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Optional<Object> detail = Optional.ofNullable(securityContext)
                .map(ctx -> ctx.getAuthentication())
                .map(authentication -> authentication.getDetails());
        if (detail.isPresent() && detail.get() instanceof LinkerAuthenticationDetail) {
            return detail.get() != null && ((LinkerAuthenticationDetail) detail.get()).getUserId() != null;
        }
        return false;
    }


    /**
     * 是否需要记住登录状态
     *
     * @return
     */
    public static Boolean getCurrentLoginNeedRemember() {
        LinkerAuthenticationDetail detail = getAuthenticationDetail();
        return detail == null ? false : detail.getRememberMe();
    }

    public static String getIP() {
        LinkerAuthenticationDetail detail = getAuthenticationDetail();
        return detail.getRemoteAddress();
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> authentication.getAuthorities().stream()
                        .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS)))
                .orElse(false);
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the isUserInRole() method in the Servlet API
     *
     * @param authority the authority to check
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> authentication.getAuthorities().stream()
                        .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority)))
                .orElse(false);
    }
}
