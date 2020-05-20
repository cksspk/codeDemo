package com.ckss.project.system.service;

import com.ckss.project.system.domain.Role;

import java.util.List;

public interface RoleService {
    /**
     * 校验角色名称是否唯一
     * @param roleName
     * @return
     */
    String checkRoleNameUnique(Role roleName);

    /**
     * 校验角色权限是否唯一
     * @param role
     * @return
     */
    String checkRoleKeyUnique(Role role);

    /**
     * 添加角色
     * @param role
     * @return
     */
    int addRole(Role role);

    /**
     * 删除角色
     * @param ids
     * @return
     */
    int deleteRoleByIds(String ids);

    /**
     * 校验角色是否允许操作
     * @param role 角色信息
     */
    void checkRoleAllowed(Role role);

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    Role selectRoleById(Long roleId);



    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    List<Role> selectRoleAll();

    /**
     * 分页查询角色信息
     * @param role
     * @return
     */
    List<Role> queryRoleList(Role role);


    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param id 用户ID
     * @return 选中角色ID列表
     */
    List<Integer> selectRoleListByUserId(Long id);
}
