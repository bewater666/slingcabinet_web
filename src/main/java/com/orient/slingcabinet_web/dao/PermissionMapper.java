package com.orient.slingcabinet_web.dao;

import com.orient.slingcabinet_web.model.Permission;
import com.orient.slingcabinet_web.model.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: zhoudun
 * @Date: 2019/3/14 14:38
 * @Func:
 * @Version 1.0
 */
@Mapper
public interface PermissionMapper {
    /**
     * 查找该角色下的权限列表
     * @param role
     * @return
     */
    List<Permission> findPermissionByRole(Role role);
}
