package com.linker.tower.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author huang.ziqing
 * @date 2019/10/24
 */

@Entity
@Table(name = "user")
public class User extends AbstractAuditingEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(length = 20)
    @ApiModelProperty("用户ID")
    private Long userId;

    @NotNull
    @Column(length = 20, unique = true, nullable = false)
    @ApiModelProperty("登录名称")
    private String username;

    @JsonIgnore
    @NotNull
    @Column(name = "password", length = 60, nullable = false)
    @ApiModelProperty("密码")
    private String password;

    @Column(name = "phone", length = 11)
    @ApiModelProperty("手机号")
    private String phone;

    @Column(name = "gender", length = 1)
    @ApiModelProperty("性别")
    private String gender;

    @Column(name = "photo", length = 100)
    @ApiModelProperty("头像")
    private String photo;

    @Column(name = "email", length = 40)
    @ApiModelProperty("邮箱")
    private String email;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
