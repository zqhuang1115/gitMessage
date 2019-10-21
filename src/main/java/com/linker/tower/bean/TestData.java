package com.linker.tower.bean;

import lombok.Data;

/**
 * @author huang.ziqing
 * @date 2019/10/12
 */

@Data
public class TestData extends BaseMessage{

    private String content;

    public TestData(String content) {
        this.content = content;
    }
}
