package com.linker.tower.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins

    //public static final String EMAIL_REGEX = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    //public static final String LOGIN_REGEX = "^[A-Za-z@_.].*[0-9]|[0-9].*[A-Za-z@_.]$";

    public static final String PHONE_NUMBER_REG = "^(1)\\d{10}$";

    public static final String IDENTITY_CODE  = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
        "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
    public static final String SYSTEM_USER = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String DEFAULT_LANGUAGE = "en";

    public static final String pass = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{5,16}$";

    private Constants() {
    }
}
