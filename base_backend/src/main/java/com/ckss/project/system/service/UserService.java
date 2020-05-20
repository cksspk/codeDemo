package com.ckss.project.system.service;

import com.ckss.project.system.domain.User;

import java.util.List;

public interface UserService {
    /**
     * 校验用户名称是否唯一
     *
     * @param userName 用户名称
     * @return 结果
     */
    String checkUserNameUnique(String userName);

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    String checkUserPhoneUnique(User user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    String checkUserEmailUnique(User user);


    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int addUser(User user);


    /**
     * 通过用户ID删除用户
     *
     * @param ids 用户ID
     * @return 结果
     */

    int deleteUserByIds(String ids);


    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    void checkUserAllowed(User user);


    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 根据用户id查询用户详情
     * @param id
     * @return
     */
    User queryUserById(Long id);

    List<User> queryUseList(User user);

    /**
     * 修改用户状态
     * @param user
     * @return
     */
    int updateUserStatus(User user);
}
