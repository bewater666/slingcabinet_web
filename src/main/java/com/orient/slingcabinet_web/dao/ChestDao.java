package com.orient.slingcabinet_web.dao;

import com.orient.slingcabinet_web.model.Chest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/8/15 9:55
 * @func
 */
@Mapper
@Repository
public interface ChestDao {
    List<Chest> findAllChest();

    String findByChestID(String chestID);
}
