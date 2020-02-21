package com.orient.slingcabinet_web.controller;

import com.orient.slingcabinet_web.dao.SweepRecordDao;
import com.orient.slingcabinet_web.model.ResultBean;
import com.orient.slingcabinet_web.model.SweepRecord;
import com.orient.slingcabinet_web.model.SweepRecordView;
import com.orient.slingcabinet_web.model.SweepRecordVo;
import com.orient.slingcabinet_web.service.SweepRecordService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhoudun
 * @Date: 2019/7/17 17:32
 * @Func:
 * @Version 1.0
 */
@RestController
@RequestMapping("/api")
public class SweepRecordController {
    @Autowired
    private SweepRecordService sweepRecordService;
    @Autowired
    private SweepRecordDao sweepRecordDao;

    @GetMapping("/findChestHaveTool/{id}")
    public ResultBean findNewSweepRecord(@PathVariable("id") String chestID){
        Subject subject = SecurityUtils.getSubject();
        // 登录了返回true
//        if (subject.isAuthenticated()) {
            return sweepRecordService.findChestNewRecord(chestID);
//        } else {
//            return new ResultBean(403, "请先登录");
//        }

    }

    @GetMapping("/findChestRecord/{id}/{currentPage}/{pageSize}")
    public ResultBean findAllSweepRecord(@PathVariable("id") String chestID,
                                         @PathVariable("currentPage") Integer currentPage,
                                         @PathVariable("pageSize") Integer pageSize){
        List<SweepRecordVo> sweepRecordVoList = sweepRecordService.findChestAllRecord(chestID,currentPage,pageSize);
        List<SweepRecord> byChestID = sweepRecordDao.findByChestID(chestID);
        Integer thisPageNum;
        if (currentPage<=0){
            return new ResultBean(201,"输入的页码不符合规范");
        }
        if (currentPage==1){
            if (sweepRecordVoList.size()<=pageSize){
                thisPageNum =  sweepRecordVoList.size();
            }else {
                thisPageNum = pageSize;
            }
        }else {
            Integer size = sweepRecordVoList.size()-(currentPage-1)*pageSize;
            if (size>=pageSize){
                thisPageNum = pageSize;
            }else {
                thisPageNum = size;
            }
        }
        if ((currentPage-1)*pageSize>sweepRecordVoList.size()){
            thisPageNum = 0;
        }
        Map map = new HashMap();
        map.put("currentPage",currentPage);
        map.put("thisPageNum",thisPageNum);
        map.put("totalNum",byChestID.size());
        map.put("sweepRecordVoList",sweepRecordVoList);
        return  new ResultBean(200,"查询成功",map);
    }

    @GetMapping("/test/{id}")
    public ResultBean test(@PathVariable("id") String chestID){
        List<SweepRecord> byChestID = sweepRecordDao.findByChestID(chestID);
        return new ResultBean(200,"查询成功",byChestID);
    }

}
