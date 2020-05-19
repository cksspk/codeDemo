package com.ckss.blog.home.system.mapper;

import com.ckss.blog.home.system.domain.Role;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *角色数据操作
 */

@Component
public interface RoleMapper {
    /**
     * 校验角色名称是否唯一
     * @param roleName
     * @return
     */
    Role checkRoleNameUnique(String roleName);

    /**
     * 校验角色权限是否唯一
     * @param roleKey
     * @return
     */
    Role checkRoleKeyUnique(String roleKey);

    /**
     * 新增角色信息
     * @param role
     * @return
     */
    int insertRole(Role role);

    /**
     * 通过id查询角色
     * @param roleId
     * @return
     */
    Role selectRoleById(Long roleId);

    /**
     * 通过角色ID删除角色
     *
     * @param ids 角色ID
     * @return 结果
     */
    int deleteRoleByIds(@Param("ids") Long[] ids, @Param("username") String username);


    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    List<Role> selectRoleAll();


    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    List<Role> selectRoleList(Role role);


    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    List<Integer> selectRoleListByUserId(Long userId);
}
