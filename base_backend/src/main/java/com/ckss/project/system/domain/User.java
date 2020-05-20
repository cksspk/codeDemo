package com.ckss.project.system.domain;


import com.ckss.framework.web.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @className: User
 * @description: 用户对象 user
 * @author: cksspk
 * @date: 2020/3/23
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends BaseEntity implements Serializable {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 密码
     */
//    @JsonIgnore
    private String password;

    /**
     * 盐加密
     */
//    private String salt;

    /**
     * 帐号状态（0正常 1停用）
     */
    @ApiModelProperty(value = "帐号状态（0正常 1停用）", example = "1")
    private String status;

    /**
     * 最后登陆IP
     */
    private String loginIp;


    /**
     * 最后登陆时间
     */
    @ApiModelProperty(value = "最后登陆时间", example = "2019-01-01 8:00:00")
    private Date loginDate;


    /**
     * 角色对象
     */

    private List<Role> roles;


    /**
     * 角色组
     */
    private Long[] roleIds;

    public User(){}


    public User(Long id){
        this.id = id;
    }


    @JsonIgnore
    public boolean isAdmin() {
        return isAdmin(this.id);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
}
