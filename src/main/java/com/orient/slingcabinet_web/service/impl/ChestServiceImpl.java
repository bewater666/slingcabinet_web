package com.orient.slingcabinet_web.service.impl;

import com.orient.slingcabinet_web.dao.ChestDao;
import com.orient.slingcabinet_web.dao.SweepRecordDao;
import com.orient.slingcabinet_web.dao.ToolDao;
import com.orient.slingcabinet_web.model.*;
import com.orient.slingcabinet_web.service.ChestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/8/15 11:17
 * @func
 */
@Service
public class ChestServiceImpl implements ChestService {
    @Autowired
    private ChestDao chestDao;
    @Autowired
    private SweepRecordDao sweepRecordDao;
    @Autowired
    private ToolDao toolDao;

    /**
     * 该业务功能:查询所有柜子的状态 逻辑:查询每个柜子最新的一条扫描记录 来判断柜子的状态
     * @return
     */
    @Override
    public ResultBean findAllChestStatus() {
        List<Chest> chestList = chestDao.findAllChest();
        List<ChestView> chestViewList = new ArrayList<>();
        for (Chest chest:
        chestList) {
            ChestView chestView = new ChestView();
            List<SweepRecord> sweepRecordList = sweepRecordDao.findByChestID(chest.getChestID());
            if (sweepRecordList.size()==0){//即未对该柜子进行扫描
                chestView.setStatus(2);
                chestView.setChestName(chest.getChestName());
                chestView.setChestID(chest.getChestID());
                chestViewList.add(chestView);
            }else{
                //因为是倒叙查询 所以这里应该选第一个 就是最后一个
                SweepRecord sweepRecord = sweepRecordList.get(0);//该柜子最新的一条扫描记录
                Integer sweepNums = sweepRecord.getNums();//扫描到的该柜子中工具的数目
                List<Tool> toolList = toolDao.findByChestID(chest.getChestID());
                Integer realNum = toolList.size();//该柜子中实际的工具数据
                if (sweepNums!=realNum){//只要扫描的数目和实际的数据不等 即判断柜子状态异常
                    chestView.setStatus(0);
                    chestView.setChestName(chest.getChestName());
                    chestView.setChestID(chest.getChestID());
                    chestViewList.add(chestView);
                }else{//数目相等也不一定正常  可能少了一个自己的   多了一个别的柜子的
                    //判断逻辑:到这里数目是对了 只需要遍历扫描到的标签集合  toolIDs  根据协议标签id 含柜子id 即可得出结论
                    //0C000000010000020000000000000000  其中000002即为柜子的id号
                    String toolIDs = sweepRecord.getToolIDs();
                    String[] split = toolIDs.split(",");
                    List<String> toolIDList = Arrays.asList(split);
                    boolean flag =true;
                    for (String toolID:
                    toolIDList) {
                        String subChestID = toolID.substring(10, 18);
                        if (!subChestID.equals(chest.getChestID())){
                            flag = false;//即不属于这个柜子  状态异常
                        }
                    }
                    if (flag){//正常
                        chestView.setStatus(1);
                        chestView.setChestName(chest.getChestName());
                        chestView.setChestID(chest.getChestID());
                        chestViewList.add(chestView);
                    }else {
                        chestView.setStatus(0);
                        chestView.setChestName(chest.getChestName());
                        chestView.setChestID(chest.getChestID());
                        chestViewList.add(chestView);
                    }
                }
            }


        }
        ChestViewReal chestViewReal = new ChestViewReal();
        Integer num_status0 = 0;
        Integer num_status1 = 0;
        Integer num_status2 = 0;
        for (ChestView chestView:
        chestViewList) {
            if (chestView.getStatus()==0){
                num_status0++;
            }
            if (chestView.getStatus()==1){
                num_status1++;
            }
            if (chestView.getStatus()==2){
                num_status2++;
            }
        }
        chestViewReal.setNum_status0(num_status0);
        chestViewReal.setNum_status1(num_status1);
        chestViewReal.setNum_status2(num_status2);
        chestViewReal.setChestViewList(chestViewList);
        return new ResultBean(200,"查询成功",chestViewReal);
    }
}
