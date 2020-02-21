package com.orient.slingcabinet_web.dao;

import com.orient.slingcabinet_web.model.SweepRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: zhoudun
 * @Date: 2019/7/17 14:23
 * @Func:
 * @Version 1.0
 */
@Mapper
@Repository
public interface SweepRecordDao {
    List<SweepRecord> findAll();

    /**
     * 查询某个柜子的所有扫描记录
     * @return
     */
    List<SweepRecord> findByChestID(String chestID);
}
