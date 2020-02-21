package com.orient.slingcabinet_web.dao;

import com.orient.slingcabinet_web.model.Tool;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: zhoudun
 * @Date: 2019/7/17 9:54
 * @Func:
 * @Version 1.0
 */
@Mapper
@Repository
public interface ToolDao {
    List<Tool> findAll();

    List<Tool> findByChestID(String chestID);

    Tool findByToolName(String toolName);

    Tool findByToolID(String toolID);

    Integer countByToolNameAndChestID(@Param("toolName") String toolName, @Param("chestID") String chestID);
}