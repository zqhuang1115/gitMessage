package com.linker.tower.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author liu.jj
 * @date 2019/09/25
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
