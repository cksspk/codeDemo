package com.ckss.blog.home.system.mapper;


import com.ckss.blog.home.system.domain.RoleMenu;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @className: RoleMenuMapper
 * @description: 角色与菜单关联表 数据层
 * @author: cksspk
 * @date: 2020/3/23
 */

@Component
public interface RoleMenuMapper {
    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    int checkMenuExistRole(Long menuId);

    /**
     * 通过角色ID删除角色和菜单关联
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int deleteRoleMenuByRoleId(Long roleId);

    /**
     * 批量新增角色菜单信息
     *
     * @param roleMenuList 角色菜单列表
     * @return 结果
     */
    int batchRoleMenu(List<RoleMenu> roleMenuList);
}
