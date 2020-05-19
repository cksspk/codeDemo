package com.ckss.blog.home.system.service.impl;

import com.ckss.blog.common.constant.UserConstants;
import com.ckss.blog.common.exception.BlogException;
import com.ckss.blog.common.utils.ConvertUtils;
import com.ckss.blog.common.utils.StringUtils;
import com.ckss.blog.home.system.domain.User;
import com.ckss.blog.home.system.domain.UserRole;
import com.ckss.blog.home.system.mapper.UserMapper;
import com.ckss.blog.home.system.mapper.UserRoleMapper;
import com.ckss.blog.home.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: UserServiceImpl
 * @description: TODO
 * @author: cksspk
 * @date: 2020/3/28
 **/

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public String checkUserNameUnique(String userName) {
        int count = userMapper.checkUserNameUnique(userName);
        return count == 0 ? UserConstants.UNIQUE : UserConstants.NOT_UNIQUE;
    }

    @Override
    public String checkUserPhoneUnique(User user) {
        //确保userId不为null,sql中不包含该条件(id)
        Long userId = StringUtils.isNull(user.getId()) ? -1L : user.getId();
        User selectOne = userMapper.checkUserPhoneUnique(user.getPhone());
        //校验是否是同一用户
        if(selectOne != null && userId != selectOne.getId()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkUserEmailUnique(User user) {
        //确保userId不为null,sql中不包含该条件(id)
        Long userId = StringUtils.isNull(user.getId()) ? -1L : user.getId();
        User selectOne = userMapper.checkEmailUnique(user.getEmail());
        //校验是否是同一用户
        if(selectOne != null && userId != selectOne.getId()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }


    @Override
    @Transactional
    public int addUser(User user) {
        return  userMapper.insertUser(user);
    }

    @Override
    @Transactional
    public int deleteUserByIds(String ids) {
        Long[] userIds = ConvertUtils.toLongArray(ids);
        for (Long userId : userIds) {
            //检查操作
            checkUserAllowed(new User(userId));
            //删除用户与角色关联
            userMapper.deleteUserRoleByUserId(userId);
        }

        //使用当前登录用户，这里使用测试账号  TODO
        String loginUsername = "test_Del_Name";
        return userMapper.deleteUserByIds(userIds, loginUsername);
    }

    @Override
    public void checkUserAllowed(User user) {
        if (StringUtils.isNotNull(user.getId()) && user.isAdmin()) {
            //使用枚举的形式
//            throw new BlogException(ResultCodeEnum.NOT_ALLOW);
            throw new BlogException(400,"不允许操作超级管理员用户");
        }

    }

    @Override
    @Transactional
    public int updateUser(User user) {
        Long userId = user.getId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);

        // 新增用户与角色管理
        insertUserRole(user);
        return userMapper.updateUser(user);
    }

    @Override
    public User queryUserById(Long id) {
        return userMapper.queryUserById(id);
    }

    @Override
    public List<User> queryUseList(User user) {
        return userMapper.queryUseList(user);
    }

    @Override
    public int updateUserStatus(User user) {

        return userMapper.updateUser(user);
    }

    /**
     * 新增用户与角色管理
     * 一个用户可对应多个角色
     * @param user
     */
    public void insertUserRole(User user) {
        Long[] roleIds = user.getRoleIds();
        if(roleIds.length > 0){
            List<UserRole> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                UserRole userRole = new UserRole(user.getId(),roleId);
                list.add(userRole);
            }
            if(!list.isEmpty()){
                userRoleMapper.batchUserRole(list);
            }
        }

    }
}
