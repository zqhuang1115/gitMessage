package com.linker.tower.web.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wang.gs
 * @date 2019/07/10
 */
@Data
public class UserBaseInfo {

    @ApiModelProperty("用户ID")
    private Long userId;

    @NotNull
    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("头像")
    private String photo;

    @ApiModelProperty("融云token")
    private String rcToken;

    @ApiModelProperty("是否是服务者")
    private Boolean isPro;
}
