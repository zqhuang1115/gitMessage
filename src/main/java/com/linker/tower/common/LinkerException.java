package com.linker.tower.common;

/**
 * @author wang.gs
 * @date 2019/05/23
 */
public class LinkerException extends RuntimeException {

    private String errorMessage;

    public LinkerException(String errorMessage){
        super(errorMessage);
    }

    public LinkerException(Throwable throwable){
        super(throwable);
    }
}
