package com.orient.slingcabinet_web.service;

import com.orient.slingcabinet_web.model.ResultBean;
import com.orient.slingcabinet_web.model.SweepRecord;
import com.orient.slingcabinet_web.model.SweepRecordView;
import com.orient.slingcabinet_web.model.SweepRecordVo;

import java.util.List;

/**
 * @Author: zhoudun
 * @Date: 2019/7/17 14:27
 * @Func:
 * @Version 1.0
 */

public interface SweepRecordService {
    ResultBean findChestNewRecord(String chestID);

    List<SweepRecordVo> findChestAllRecord(String chestID, Integer currentPage, Integer pageSize);


}
