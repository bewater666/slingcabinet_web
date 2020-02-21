package com.orient.slingcabinet_web.service.impl;

import com.github.pagehelper.PageHelper;
import com.orient.slingcabinet_web.dao.ChestDao;
import com.orient.slingcabinet_web.dao.SweepRecordDao;
import com.orient.slingcabinet_web.dao.ToolDao;
import com.orient.slingcabinet_web.model.*;
import com.orient.slingcabinet_web.service.SweepRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: zhoudun
 * @Date: 2019/7/17 14:28
 * @Func:
 * @Version 1.0
 */
@Service
@Slf4j
public class SweepRecordServiceImpl implements SweepRecordService {
    @Autowired
    private SweepRecordDao sweepRecordDao;
    @Autowired
    private ToolDao toolDao;
    @Autowired
    private ChestDao chestDao;


    @Override
    public ResultBean findChestNewRecord(String chestID) {
        List<Tool> tools = toolDao.findByChestID(chestID);
        List<SweepRecord> sweepRecords = sweepRecordDao.findByChestID(chestID);

        if (sweepRecords.size()==0){
            return new ResultBean(200,"暂时还没有"+chestID+"柜子的扫描数据");
        }
        String time = sweepRecords.get(0).getTime();    //因为从数据库查询的是倒叙
        List<Tool> toolList = toolDao.findByChestID(chestID); //根据最新的一条扫描记录的柜子id查询该柜子下应有的工具列表
        List<String> toolNames = new ArrayList<>(); //该柜子下应有的工具名称集合
        List<String> toolIDs1= new ArrayList<>(); //该柜子下应有的工具名称集合
        for (Tool tool:
             toolList) {
            String toolName = tool.getToolName();
            toolNames.add(toolName);
        }
        for (Tool tool:
                toolList) {
            String toolID = tool.getToolID();
            toolIDs1.add(toolID);
        }
        Integer num = sweepRecords.get(0).getNums();//扫描到的工具数目
        String toolNames1 = sweepRecords.get(0).getToolNames();//扫描到的工具名称集
        String toolIDs = sweepRecords.get(0).getToolIDs();//扫描到的工具id集
        String[] split1 = toolIDs.split(",");
        List<String> toolIDList = Arrays.asList(split1);//扫描到的工具id集合
        String[] split = toolNames1.split(",");
        List<String> toolNames2 = Arrays.asList(split);//扫描到的工具名称集合
        toolNames.removeAll(toolNames2);//求这两个集合的差集  得到未扫描到的丢失的标签
        toolIDs1.removeAll(toolIDList);
//        System.out.println("丢失的工具名称====="+toolNames);
        System.out.println("丢失的工具id====="+toolIDs1);
        List<ToolView> toolViews = new ArrayList<>();
        if (toolIDs1.size()==tools.size()) {//即全丢的情况下
            log.warn("这是工具全丢的情况");
            for (String toolID :
                    toolIDs1) {
                Tool tool = toolDao.findByToolID(toolID);
                ToolView toolView = new ToolView();
                toolView.setStatus(0);
                toolView.setToolID(tool.getToolID());
                toolView.setToolName(tool.getToolName());
                toolView.setCount(1);
                toolView.setLiftingEquipName(tool.getLiftingEquipName());
                toolView.setStandard(tool.getStandard());
                toolView.setTonnage(tool.getTonnage());
                toolView.setStartTime(tool.getStartTime());
                toolViews.add(toolView);
            }
        }else{
            for (String toolID:
                    toolIDList) {
                Tool tool = toolDao.findByToolID(toolID);
                String chestID1 = tool.getChestID();
                ToolView toolView = new ToolView();
                if (chestID1.equals(chestID)){//即该扫描到的在该柜子下    正常的
                    toolView.setStatus(1);
                    toolView.setToolID(tool.getToolID());
                    toolView.setToolName(tool.getToolName());
//                Integer count = toolDao.countByToolNameAndChestID(tool.getToolName(), chestID);//该柜子下某一产品的个数

                    toolView.setCount(1);
                    toolView.setLiftingEquipName(tool.getLiftingEquipName());
                    toolView.setStandard(tool.getStandard());
                    toolView.setTonnage(tool.getTonnage());
                    toolView.setStartTime(tool.getStartTime());
                    toolViews.add(toolView);
                }else {//即该扫描到的不在该柜子下           多出的
                    toolView.setStatus(2);
                    toolView.setToolID(tool.getToolID());
                    toolView.setToolName(tool.getToolName());
//                Integer count = toolDao.countByToolNameAndChestID(tool.getToolName(), chestID1);
                    toolView.setCount(1);
                    toolView.setLiftingEquipName(tool.getLiftingEquipName());
                    toolView.setStandard(tool.getStandard());
                    toolView.setTonnage(tool.getTonnage());
                    toolView.setStartTime(tool.getStartTime());
                    toolViews.add(toolView);
                }

            }

            for (String toolID://缺失的
                    toolIDs1) {
                Tool tool = toolDao.findByToolID(toolID);
                ToolView toolView = new ToolView();
                toolView.setStatus(0);
                toolView.setToolID(tool.getToolID());
                toolView.setToolName(tool.getToolName());
//            Integer count = toolDao.countByToolNameAndChestID(tool.getToolName(), chestID);
                toolView.setCount(1);
                toolView.setLiftingEquipName(tool.getLiftingEquipName());
                toolView.setStandard(tool.getStandard());
                toolView.setTonnage(tool.getTonnage());
                toolView.setStartTime(tool.getStartTime());
                toolViews.add(toolView);
            }
        }

        System.out.println("初始="+toolViews);
        List<ToolView> newToolViewList = new ArrayList<>();
        String toolID = "";
        for (ToolView oldToolView:
        toolViews) {
            boolean flag = false;
            for (ToolView newToolView:
            newToolViewList) {
                if ((newToolView.getToolName().equals(oldToolView.getToolName()))&&(newToolView.getStatus().equals(oldToolView.getStatus()))) {
                    // 新的List中存在名字相同的，则count+1
                    toolID = newToolView.getToolID()+","+oldToolView.getToolID();
                    newToolView.setStatus(oldToolView.getStatus());
                    newToolView.setToolName(oldToolView.getToolName());
                    newToolView.setToolID(toolID);
                    newToolView.setTonnage(oldToolView.getTonnage());
                    newToolView.setStandard(oldToolView.getStandard());
                    newToolView.setLiftingEquipName(oldToolView.getLiftingEquipName());
                    newToolView.setStartTime(oldToolView.getStartTime());
                    newToolView.setCount(newToolView.getCount()+1);

                    flag=true;
                }
            }
            // 新的List中不存在
            if (!flag) {
                newToolViewList.add(oldToolView);
            }
        }
        System.out.println(newToolViewList);
        SweepRecordView sweepRecordView = new SweepRecordView();
        sweepRecordView.setChestID(chestID);
        String chestName = chestDao.findByChestID(chestID);
        sweepRecordView.setChestName(chestName);
        sweepRecordView.setTime(time);
        sweepRecordView.setNum(num);
        Integer chestStatus = 1;
        for (ToolView toolView:
        newToolViewList) {
            if (toolView.getStatus()==0||toolView.getStatus()==2){
                chestStatus = 0;
            }
        }
        sweepRecordView.setChestStatus(chestStatus);
        sweepRecordView.setSweepToolList(newToolViewList);



        //查最新的一条记录
        return new ResultBean(200,"查询成功",sweepRecordView);
    }

    @Override
    public List<SweepRecordVo>  findChestAllRecord(String chestID,Integer currentPage,Integer pageSize) {
        List<Tool> tools = toolDao.findByChestID(chestID);
        PageHelper.startPage(currentPage,pageSize);
        List<SweepRecord> sweepRecords = sweepRecordDao.findByChestID(chestID);
        String chestName = chestDao.findByChestID(chestID);

        List<SweepRecordVo> sweepRecordVoList = new ArrayList<>();
        for (SweepRecord sweepRecord:
        sweepRecords) {
            SweepRecordVo sweepRecordVo = new SweepRecordVo();
            sweepRecordVo.setTime(sweepRecord.getTime());
            sweepRecordVo.setChestStatus(sweepRecord.getChestStatus());
            sweepRecordVo.setChestName(chestName);
            //扫描到的
            String sweepToolIDs = sweepRecord.getToolIDs();

            List<ToolView> sweepToolIDListRel = new ArrayList<>();
            if (!sweepToolIDs.equals("空")){
                String[] split = sweepToolIDs.split(",");
                List<String> sweepToolIDList = Arrays.asList(split);

                for (String toolID:
                        sweepToolIDList) {
                    ToolView sweepToolView = new ToolView();
                    Tool tool = toolDao.findByToolID(toolID);
                    sweepToolView.setStartTime(tool.getStartTime());
                    sweepToolView.setLiftingEquipName(tool.getLiftingEquipName());
                    sweepToolView.setStandard(tool.getStandard());
                    sweepToolView.setTonnage(tool.getTonnage());
                    sweepToolView.setCount(1);
                    sweepToolView.setToolID(toolID);
                    sweepToolView.setToolName(tool.getToolName());
                    sweepToolIDListRel.add(sweepToolView);
                }
            }else{
                sweepToolIDListRel = null;
            }
            sweepRecordVo.setSweepToolList(sweepToolIDListRel);
            //缺失的
            String missedToolIDs = sweepRecord.getMissedToolIDs();

            List<ToolView> missedToolIDListRel = new ArrayList<>();
            if (!missedToolIDs.equals("空")){
                String[] split = missedToolIDs.split(",");
                List<String> missedToolIDList = Arrays.asList(split);

                for (String toolID:
                        missedToolIDList) {
                    ToolView missedToolView = new ToolView();
                    Tool tool = toolDao.findByToolID(toolID);
                    missedToolView.setStartTime(tool.getStartTime());
                    missedToolView.setLiftingEquipName(tool.getLiftingEquipName());
                    missedToolView.setStandard(tool.getStandard());
                    missedToolView.setTonnage(tool.getTonnage());
                    missedToolView.setCount(1);
                    missedToolView.setStatus(0);
                    missedToolView.setToolID(toolID);
                    missedToolView.setToolName(tool.getToolName());
                    missedToolIDListRel.add(missedToolView);
                }
            }else{
                missedToolIDListRel = null;
            }
            sweepRecordVo.setMissedToolList(missedToolIDListRel);
            //多出的的
            String extraToolIDs = sweepRecord.getExtraToolIDs();

            List<ToolView> extraToolIDListRel = new ArrayList<>();
            if (!extraToolIDs.equals("空")){
                String[] split1 = extraToolIDs.split(",");
                List<String> extraToolIDList = Arrays.asList(split1);
                for (String toolID:
                        extraToolIDList) {
                    ToolView extraToolView = new ToolView();
                    Tool tool = toolDao.findByToolID(toolID);
                    extraToolView.setStartTime(tool.getStartTime());
                    extraToolView.setLiftingEquipName(tool.getLiftingEquipName());
                    extraToolView.setStandard(tool.getStandard());
                    extraToolView.setTonnage(tool.getTonnage());
                    extraToolView.setCount(1);
                    extraToolView.setStatus(2);
                    extraToolView.setToolID(toolID);
                    extraToolView.setToolName(tool.getToolName());
                    extraToolIDListRel.add(extraToolView);
                }
            }else{
                extraToolIDListRel = null;
            }

            sweepRecordVo.setExtraToolList(extraToolIDListRel);
            sweepRecordVoList.add(sweepRecordVo);
        }
        Integer totalNum = sweepRecordVoList.size();
        PageBean<SweepRecordVo> pageData = new PageBean<>(currentPage,pageSize,totalNum);
        pageData.setItems(sweepRecordVoList);
        return pageData.getItems();
}
}
