package com.orient.slingcabinet_web.dao;

import com.orient.slingcabinet_web.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: zhoudun
 * @Date: 2019/3/14 14:36
 * @Func:
 * @Version 1.0
 */
@Mapper
public interface UserMapper {
    /**
     * @func 根据账号查找用户
     * @param account
     * @return
     */
    User selectOne(String account);


}
