package com.ckss.blog.home.system.mapper;

import com.ckss.blog.home.system.domain.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface UserMapper {

    /**
     * 校验用户名是否存在
     * @param userName
     * @return
     */
    @Select("select count(1) from user where user_name = #{userName}")
    int checkUserNameUnique(@Param("userName") String userName);

    /**
     * 删除用户与角色关联
     * @param userId
     * @return
     */
    @Delete("delete from sys_user_role where user_id=#{userId}")
    int deleteUserRoleByUserId(@Param("userId") Long userId);


    /**
     * 通过用户ID删除用户
     *
     * @param ids 用户id
     * @param loginUsername 操作名字
     * @return
     */
    int deleteUserByIds(Long[] ids, String loginUsername);

    /**
     * 校验手机号码是否唯一
     * @param phone
     * @return
     */
    User checkUserPhoneUnique(String phone);

    /**
     * 校验邮箱是否唯一
     * @param email
     * @return
     */
    User checkEmailUnique(String email);

    /**
     * 更新用户
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
     * 新增用户
     * @param user
     * @return
     */
    int insertUser(User user);
}
