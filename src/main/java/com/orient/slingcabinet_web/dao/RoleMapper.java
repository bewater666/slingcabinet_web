package com.orient.slingcabinet_web.dao;

import com.orient.slingcabinet_web.model.Role;
import com.orient.slingcabinet_web.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: zhoudun
 * @Date: 2019/3/14 14:38
 * @Func:
 * @Version 1.0
 */
@Mapper
public interface RoleMapper {

    List<Role> findRolesByUser(User user);
}
