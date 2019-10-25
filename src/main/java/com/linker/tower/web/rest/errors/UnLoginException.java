package com.linker.tower.web.rest.errors;

/**
 * @author haosheng
 * @Date 2019/03/24
 */
public class UnLoginException extends RuntimeException {
    public UnLoginException() {
        super("系统未登录");
    }
}
