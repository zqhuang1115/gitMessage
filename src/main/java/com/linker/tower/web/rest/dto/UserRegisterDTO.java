package com.linker.tower.web.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import com.linker.tower.config.Constants;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author wang.gs
 * @date 2019-05-21
 */
@Data
public class UserRegisterDTO {

    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @Size(min = 5, max = 100)
    @ApiModelProperty(value = "头像")
    private String photo;

    @Size(max = 100)
    @ApiModelProperty(value = "邮箱")
    private String email;

    @NotNull
    //@Pattern(regexp = Constants.pass)
    @Size(min = 6, max = 32)
    @ApiModelProperty(value = "密码", required = true)
    private String password;

    @Size(min = 11, max = 11)
    @Pattern(regexp = Constants.PHONE_NUMBER_REG)
    @ApiModelProperty(value = "手机号")
    private String phone;

}