package com.orient.slingcabinet_web.service.impl;

import com.orient.slingcabinet_web.dao.ToolDao;
import com.orient.slingcabinet_web.model.ResultBean;
import com.orient.slingcabinet_web.model.Tool;
import com.orient.slingcabinet_web.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zhoudun
 * @Date: 2019/7/17 10:55
 * @Func:
 * @Version 1.0
 */
@Service
public class ToolServiceImpl implements ToolService {
    @Autowired
    private ToolDao toolDao;

    @Override
    public ResultBean findAll() {
        List<Tool> toolList = toolDao.findAll();
        return new ResultBean(200,"查询成功",toolList);
    }

    @Override
    public ResultBean findByChestID(String chestID) {
        List<Tool> toolList = toolDao.findByChestID(chestID);
        return new ResultBean(200,"查询成功",toolList);
    }

    @Override
    public ResultBean findByToolName(String tooName) {
        Tool tool = toolDao.findByToolName(tooName);
        return new ResultBean(200,"查询成功",tool);
    }
}
