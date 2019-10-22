package com.linker.tower.bean;

import lombok.Data;

/**
 * @author liu.jj
 * @date 2019/09/24
 */

@Data
public class TestData extends BaseMessage{

    private String content;

    public TestData(String content) {
        this.content = content;
    }
}
