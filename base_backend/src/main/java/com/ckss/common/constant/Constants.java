package com.ckss.common.constant;

/**
 * @className: Constant
 * @description: TODO
 * @author: cksspk
 * @date: 2020/3/23
 **/

public class Constants {

    private Constants(){}

    /**
     * 登录验证码 prefix
     */
    public static final String REDIS_KEY_PREFIX = "user:verify:code:";

    /**
     * 校验返回结果码
     */
    public static final String UNIQUE = "0";
    public static final String NOT_UNIQUE = "1";

}
