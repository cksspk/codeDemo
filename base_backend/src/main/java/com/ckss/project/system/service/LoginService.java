package com.ckss.project.system.service;


/**
 * @className: LoginServiceImpl
 * @description: TODO
 * @author: cksspk
 * @date: 2020/3/23
 **/

public interface LoginService {
    /**
     * 生成验证码
     * @param code
     * @return
     */
    Object sendVerifyCode(String code);


    /**
     * 登陆验证
     * @param username
     * @param password
     * @param verifyCode
     * @return
     */
    String login(String username, String password, String verifyCode);
}
