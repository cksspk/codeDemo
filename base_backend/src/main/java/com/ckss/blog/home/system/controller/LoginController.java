package com.ckss.blog.home.system.controller;

import com.ckss.blog.common.enums.ResultCodeEnum;
import com.ckss.blog.common.exception.BlogException;
import com.ckss.blog.common.web.domain.AjaxResult;
import com.ckss.blog.common.web.vo.Result;
import com.ckss.blog.home.system.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @className: LogonController
 * @description: 登陆验证
 * @author: cksspk
 * @date: 2020/3/23
 **/
//跨域使用
@CrossOrigin
@RestController
@RequestMapping("sys")
public class LoginController {


    @Autowired
    private LoginService loginService;


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



    /**
     * 用户登录 暂缓
     * @param username
     * @param password
     * @param verifyCode
     * @return
     */
//    @PostMapping
//    public ResponseEntity<AjaxResult> login(
//            String username,String password,String verifyCode){
////        AjaxResult ajax = AjaxResult.success(loginService.login(username,password,verifyCode));
//        AjaxResult ajax = new AjaxResult();
//        ajax.put("status",0);
//        return ResponseEntity.status(HttpStatus.OK).body(ajax);
//    }


    /**
     * 根据用户名生成验证码
     * @param code
     * @return
     */
    @GetMapping("send")
    public ResponseEntity<AjaxResult> send(@RequestParam("code") String code){
        int i = 1;
        int num = 1/0;


        AjaxResult ajax = AjaxResult.success(loginService.sendVerifyCode(code));
        return ResponseEntity.status(HttpStatus.OK).body(ajax);
    }


    /**
     * 根据用户名生成验证码
     * @return
     */
    @GetMapping("test")
    public Result test(){
        try{
            int i = 1;
            int num = 1/0;
        }catch (Exception e){
            throw new BlogException(ResultCodeEnum.UNKNOWN_REASON);
        }

        return Result.ok();
    }

}
