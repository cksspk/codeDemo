package com.ckss.blog.home.system.service.impl;

import com.ckss.blog.common.constant.Constants;
import com.ckss.blog.common.utils.NumberUtils;
import com.ckss.blog.home.system.domain.User;
import com.ckss.blog.home.system.mapper.UserMapper;
import com.ckss.blog.home.system.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @className: LoginServiceImpl
 * @description: TODO
 * @author: cksspk
 * @date: 2020/3/23
 **/
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    /**
     * 生成验证码
     * @param code
     * @return
     */
    @Override
    public Object sendVerifyCode(String code) {
        //随机生成6位数字验证码
        String value = NumberUtils.generateCode(6);

        //根据用户名生成redis中的key
        String key = Constants.REDIS_KEY_PREFIX + code;

        //存入redis中，并设置过期时间
        stringRedisTemplate.opsForValue().set(key,value,5, TimeUnit.MINUTES);
        System.out.println("生成验证码:" + value);
        return value;
    }


    /**
     * 登录验证
     * @param username
     * @param password
     * @param verifyCode
     * @return
     */
    @Override
    public String login(String username, String password, String verifyCode) {
        //1. 查询用户名
        System.out.println("username = [" + username + "], password = [" + password + "], verifyCode = [" + verifyCode + "]");

        //2. 验证密码
        String code = stringRedisTemplate.opsForValue().get(Constants.REDIS_KEY_PREFIX + username);
        if(!StringUtils.equals(verifyCode,code)){
            //验证码错误
            log.info("验证码错误");
//            throw new BlException(ExceptionEnum.LOGIN_VERIFYCODE_FAILED);
        }

//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().eq(User::getUserName, username);

        //2. 验证用户
//        User user = userMapper.selectOne(queryWrapper);
//        if(user == null ){
//            log.info("用户名错误");  //用户名错误
////            throw new BlException(ExceptionEnum.LOGIN_USERNAME_FAILED);
//        }

        //3. 验证密码 暂缓
//        if(password.equals(user.getPassword())){
//
//        }

        return null;
    }
}
