package com.ckss.blog.home.system.mapper;

import com.ckss.blog.home.system.domain.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @className: UserRoleMapper
 * @description: 用户与角色关联表 数据层
 * @author: ckss
 * @date: 2020/3/30
 */
@Component
public interface UserRoleMapper{

//    delete from sys_user_role where user_id=#{userId}

    /**
     * 删除用户角色
     * @param userId
     * @return
     */
    @Delete("delete from sys_user_role where user_id = ${userId}")
    int deleteUserRoleByUserId(@Param("userId") Long userId);



    /**
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    int batchUserRole(List<UserRole> userRoleList);

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int countUserRoleByRoleId(Long roleId);
}
