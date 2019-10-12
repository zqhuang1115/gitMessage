package com.linker.gmessage.config;

public enum MsgType {

    TEXT("text", "文本"),

    MARKDOWN("markdown", "markdown类型");

    private String code;

    private String name;

    MsgType(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}