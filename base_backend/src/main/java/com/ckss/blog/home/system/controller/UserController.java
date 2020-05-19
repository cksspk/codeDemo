package com.ckss.blog.home.system.controller;

import com.ckss.blog.common.constant.UserConstants;
import com.ckss.blog.common.web.vo.Result;
import com.ckss.blog.home.system.domain.User;
import com.ckss.blog.home.system.service.RoleService;
import com.ckss.blog.home.system.service.UserService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className: UserController
 * @description: TODO
 * @author: cksspk
 * @date: 2020/3/28
 **/

@RestController
@RequestMapping("system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    RoleService roleService;



    /**
     * 状态修改
     */
    @PutMapping("/changeStatus")
    public Result changeStatus(@RequestBody User user) {
        userService.checkUserAllowed(user);
//        user.setUpdateBy(SecurityUtils.getUsername());
        //设置修改人名字 TODO
        String updateName = "updateBy_test";
        user.setUpdateBy(updateName);
        int result = userService.updateUserStatus(user);
        return result > 0 ? Result.ok() : Result.error();
    }
    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping
    public Result addUser(@RequestBody User user){
        //检测用户是否存在
        if(UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))){
            return Result.error().message("新增用户"+user.getUserName()+"失败,用户已存在");
        }else if(UserConstants.NOT_UNIQUE.equals(userService.checkUserPhoneUnique(user))) {
            return Result.error().message("新增用户" + user.getUserName() + "失败,手机号已存在");
        }else if(UserConstants.NOT_UNIQUE.equals(userService.checkUserEmailUnique(user))) {
            return Result.error().message("新增用户" + user.getUserName() + "失败,邮箱已存在");
        }

        //对输入数据进行重新组织，如密码加密， user创建者  TODO
        String createName = "test_create_Name";
        String passwd = "test_passed";

        user.setCreateBy(createName);
        user.setPassword(passwd);

        int result = userService.addUser(user);

        return result > 0 ? Result.ok() : Result.error();
    }


    /**
     * 删除用户
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public Result deleteUserByIds(@PathVariable String ids){
        int result = userService.deleteUserByIds(ids);
        return result > 0 ? Result.ok() : Result.error();
    }

    /**
     * 更新用户
     * @param user
     * @return
     */
    @PutMapping
    public Result updateUser(@RequestBody User user){
        userService.checkUserAllowed(user);
        if(UserConstants.NOT_UNIQUE.equals(userService.checkUserPhoneUnique(user))) {
            return Result.error().message("修改用户" + user.getUserName() + "失败,手机号已存在");
        }else if(UserConstants.NOT_UNIQUE.equals(userService.checkUserEmailUnique(user))) {
            return Result.error().message("修改用户" + user.getUserName() + "失败,邮箱已存在");
        }


        //更新的时候获取当前登录的用户信息 TODO
        String userName = "test_update_Name";
        user.setUpdateBy(userName);

        int result = userService.updateUser(user);

        return result > 0 ? Result.ok() : Result.error();
    }


    /**
     * 根据用户id查询用户详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result queryUserById(@PathVariable Long id){
        //获取用户详细信息
        User user = userService.queryUserById(id);
        //获取当前用户id所拥有的角色id
        List<Integer> roleIds = roleService.selectRoleListByUserId(id);
        return Result.ok().data("user",user).data("roleIds",roleIds);
    }

    /**
     * 分页查找
     * @param user
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public Result queryUserList(
            User user,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            @RequestParam(value = "orderBy",defaultValue = "create_time") String orderBy,
            @RequestParam(value = "IS_ASC",defaultValue = "false") Boolean desc
            ){

        //orderBy 条件拼接增加校验  TODO
        orderBy = orderBy + (desc ? " ASC" : " DESC");
        //拼接条件
        PageMethod.startPage(pageNum, pageSize, orderBy);

        //执行查询
        List<User> users = userService.queryUseList(user);

        PageInfo<User> objectPageInfo = new PageInfo<>(users);
        long total = objectPageInfo.getTotal();

        return Result.ok().data("userList",users).data("totalPage",total);
    }

}
