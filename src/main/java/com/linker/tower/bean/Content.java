package com.linker.tower.bean;

import lombok.Data;

/**
 * @author liu.jj
 * @date 2019/09/24
 */
@Data
public class Content<T> {

    private Project project;
    private T meagess;
}
