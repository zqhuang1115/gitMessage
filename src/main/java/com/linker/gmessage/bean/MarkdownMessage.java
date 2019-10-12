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
public class MarkdownMessage<T extends BaseMessage> {

    @JsonProperty("msgtype")
    private String msgType;

    @JsonProperty("markdown")
    private T baseContent;

    public MarkdownMessage(T baseContent) {
        this.baseContent = baseContent;
    }
}
