package com.ckss.project.system.controller;

import com.ckss.common.constant.UserConstants;
import com.ckss.framework.web.domain.AjaxResult;
import com.ckss.framework.web.vo.Result;
import com.ckss.project.system.domain.Role;
import com.ckss.project.system.service.RoleService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className: RoleController
 * @description: 角色信息
 * @author: cksspk
 * @date: 2020/3/30
 **/

@RestController
@RequestMapping("system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 新增角色
     * @param role
     * @return
     */
    @PostMapping
    public Result addRole(@RequestBody Role role){
        //检测角色是否存在
        if(UserConstants.NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))){
            return Result.error().message("新增角色" + role.getRoleName() + "失败,角色已存在");
        }else if(UserConstants.NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return Result.error().message("新增角色" + role.getRoleName() + "失败,角色权限已存在");
        }

        //通过scurity获取当前操作用户，设置为创建人  TODO
        String createBy = "test_create";
        role.setCreateBy(createBy);

        int count = roleService.addRole(role);
        return count > 0 ?Result.ok() : Result.error();
    }


    /**
     * 删除角色
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public Result deleteUserByIds(@PathVariable String ids){
        int count = roleService.deleteRoleByIds(ids);
        return count > 0 ?Result.ok() : Result.error();
    }


    /**
     * 分页查找
     * @param role
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public Result queryRoleList(
            Role role,
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
        List<Role> roles = roleService.queryRoleList(role);

        PageInfo<Role> objectPageInfo = new PageInfo<>(roles);
        long total = objectPageInfo.getTotal();

        return Result.ok().data("roleList",roles).data("totalPage",total);
    }



    /**
     * 获取角色选择框列表
     */
    @GetMapping("/optionSelect")
    public AjaxResult optionSelect() {
//        List<Role> roles = roleService.selectRoleAll();
        return AjaxResult.success(roleService.selectRoleAll());
//        return Result.ok().data("roles",roles);
    }


//
//    /**
//     * 更新用户
//     * @param user
//     * @return
//     */
//    @PutMapping
//    public Result updateUser(@RequestBody User user){
//        roleService.checkUserAllowed(user);
//        if(UserConstants.NOT_UNIQUE.equals(roleService.checkUserPhoneUnique(user))) {
//            return Result.error().message("修改用户" + user.getUserName() + "失败,手机号已存在");
//        }else if(UserConstants.NOT_UNIQUE.equals(roleService.checkUserEmailUnique(user))) {
//            return Result.error().message("修改用户" + user.getUserName() + "失败,邮箱已存在");
//        }
//
//
//        //更新的时候获取当前登录的用户信息 TODO
//        String userName = "test_update_Name";
//        user.setUpdateBy(userName);
//
//        int result = roleService.updateUser(user);
//
//        if(result > 0){
//            return Result.ok();
//        }else{
//            return Result.error();
//        }
//    }
//
//
//    /**
//     * 根据用户id查询用户详情
//     * @param id
//     * @return
//     */
//    @GetMapping("/{id}")
//    public Result queryUserById(@PathVariable Long id){
//        User user = userService.queryUserById(id);
//        return Result.ok().data("user",user);
//    }
//








}
