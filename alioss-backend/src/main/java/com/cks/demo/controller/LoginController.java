package com.cks.demo.controller;

import com.cks.demo.vo.Result;
import org.springframework.web.bind.annotation.*;

/**
 * @className: LoginController
 * @description: 模拟登陆controller.
 * @author: cksspk
 * @date: 2020/4/20
 **/
@CrossOrigin
@RestController
@RequestMapping("")
public class LoginController {


    /**
     * ********** 模拟登录
     */
    @GetMapping("info")
    public Result info(){

        System.out.println("info");
        return Result.ok().data("roles","[admin]")
                .data("name","admin")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
    @PostMapping("login")
    public Result login(String username,String password,String verifyCode){
        Result ajax = Result.ok().data("token","admin");
        return ajax;
    }
}
