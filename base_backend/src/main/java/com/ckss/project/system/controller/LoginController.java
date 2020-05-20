package com.ckss.project.system.controller;

import com.ckss.common.enums.ResultCodeEnum;
import com.ckss.common.exception.BlogException;
import com.ckss.framework.web.domain.AjaxResult;
import com.ckss.framework.web.vo.Result;
import com.ckss.project.system.service.LoginService;
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
@RestController
@RequestMapping("sys")
public class LoginController {


    @Autowired
    private LoginService loginService;


    /**
     * ********** 模拟登录
     */
    @GetMapping("info")
    public AjaxResult info(){
            System.out.println("模拟登录");

            AjaxResult ajax = AjaxResult.success();
            ajax.put("roles","[admin]");
            ajax.put("name","admin");
            ajax.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return ajax;
    }

    @PostMapping("login")
    public AjaxResult login(String username,String password,String verifyCode){
        AjaxResult ajax = AjaxResult.success();
        ajax.put("token","admin");
        return ajax;
    }



    /**
     * 模拟登陆 v1.0 使用Result链式写法
     * @return
     */
//    @GetMapping("info")
//    public Result info1(){
//        System.out.println("模拟登录");
//        return Result.ok().data("roles","[admin]")
//                .data("name","admin")
//                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
//    }
//
//    @PostMapping("login")
//    public Result login1(String username,String password,String verifyCode){
//        Result ajax = Result.ok().data("token","admin");
//        return ajax;
//    }




    /**
     * 登出接口
     * 使用springSecurity则不使用此接口
     * @return
     */
    @PostMapping("logout")
    public AjaxResult logout(){
        System.out.println("登出操作");
//        Result ajax = Result.ok();

        AjaxResult ajax = AjaxResult.success();
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
