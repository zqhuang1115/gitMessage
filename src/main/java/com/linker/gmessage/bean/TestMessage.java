package com.linker.gmessage.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author huang.ziqing
 * @date 2019/10/12
 */
@Data
@JsonInclude( JsonInclude.Include.NON_NULL)
public class TestMessage<T extends BaseMessage> {

    @JsonProperty("msgtypehh")
    private String msgType;

    @JsonProperty("text")
    private T baseContent;

    public TestMessage(T baseContent) {
        this.baseContent = baseContent;
    }
}
