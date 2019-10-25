package com.linker.tower.security;

public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    /**
     * 角色管理
     */
    public static final String role = "role:view";
    public static final String roleAdd = "role:add";
    public static final String roleEdit = "role:edit";
    public static final String roleDelete = "role:delete";
    public static final String roleEnable = "role:enable";
    public static final String roleDisable = "role:disable";

    /**
     * 操作员管理
     */
    public static final String user = "user:view";
    public static final String userAdd = "user:add";
    public static final String userEdit = "user:edit";
    public static final String userDelete = "user:delete";
    public static final String userEnable = "user:enable";
    public static final String userDisable = "user:disable";

    private AuthoritiesConstants() {
    }
}
