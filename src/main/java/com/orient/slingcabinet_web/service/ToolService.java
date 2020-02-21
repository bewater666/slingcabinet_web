package com.orient.slingcabinet_web.service;

import com.orient.slingcabinet_web.model.ResultBean;

/**
 * @Author: zhoudun
 * @Date: 2019/7/17 10:54
 * @Func:
 * @Version 1.0
 */

public interface ToolService {
    ResultBean findAll();

    ResultBean findByChestID(String chestID);

    ResultBean findByToolName(String tooName);
}
