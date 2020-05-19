package com.ckss.blog.home.system.service.impl;

import com.ckss.blog.common.constant.UserConstants;
import com.ckss.blog.common.enums.ResultCodeEnum;
import com.ckss.blog.common.exception.BlogException;
import com.ckss.blog.common.utils.ConvertUtils;
import com.ckss.blog.common.utils.StringUtils;
import com.ckss.blog.home.system.domain.Role;
import com.ckss.blog.home.system.domain.RoleMenu;
import com.ckss.blog.home.system.mapper.RoleMapper;
import com.ckss.blog.home.system.mapper.RoleMenuMapper;
import com.ckss.blog.home.system.mapper.UserRoleMapper;
import com.ckss.blog.home.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: RoleServiceImpl
 * @description: TODO
 * @author: cksspk
 * @date: 2020/3/30
 **/

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired

    private UserRoleMapper userRoleMapper;

    @Override
    public Role selectRoleById(Long roleId) {
        return roleMapper.selectRoleById(roleId);
    }

    @Override
    public List<Role> selectRoleAll() {
        return roleMapper.selectRoleAll();
    }

    @Override
    public List<Role> queryRoleList(Role role) {
        return roleMapper.selectRoleList(role);
    }

    @Override
    public List<Integer> selectRoleListByUserId(Long id) {
        return roleMapper.selectRoleListByUserId(id);
    }

    @Override
    public String checkRoleNameUnique(Role role) {
        //校验角色id必须存在
        Long id = StringUtils.isNull(role.getId()) ? -1L : role.getId();
        //查询角色数据库
        Role info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if(StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkRoleKeyUnique(Role role) {
        //校验角色id必须存在
        Long id = StringUtils.isNull(role.getId()) ? -1L : role.getId();
        //查询角色数据库
        Role info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if(StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue()){
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    @Transactional
    public int addRole(Role role) {
        // 新增角色信息
        roleMapper.insertRole(role);
        return insertRoleMenu(role);
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public int insertRoleMenu(Role role) {
        int rows = 1;
        // 新增角色与菜单管理
        List<RoleMenu> list = new ArrayList<>();
        for (Long menuId : role.getMenuIds()) {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(role.getId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (!list.isEmpty()) {
            rows = roleMenuMapper.batchRoleMenu(list);
        }
        return rows;
    }



    @Override
    public int deleteRoleByIds(String ids) {
        Long[] roleIds = ConvertUtils.toLongArray(ids);
        for (Long roleId : roleIds) {
            checkRoleAllowed(new Role(roleId));
            Role role = selectRoleById(roleId);
            //检查角色是否使用
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new BlogException(500, String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        //获取当前操作用户名称  TODO
//        String loginUsername = SecurityUtils.getUsername();
        String loginUsername = "test_del_Name";
        return roleMapper.deleteRoleByIds(roleIds, loginUsername);
    }

    /**
     * 检测角色是否正在使用
     * @param roleId
     * @return
     */
    private int countUserRoleByRoleId(Long roleId) {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }

    @Override
    public void checkRoleAllowed(Role role) {
        if (StringUtils.isNotNull(role.getId()) && role.isAdmin()) {
            //不允许操作管理员权限
            throw new BlogException(ResultCodeEnum.NOT_ALLOW);
        }
    }
}
