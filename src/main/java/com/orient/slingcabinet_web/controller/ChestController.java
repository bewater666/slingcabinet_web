package com.orient.slingcabinet_web.controller;

import com.orient.slingcabinet_web.model.ResultBean;
import com.orient.slingcabinet_web.service.ChestService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bewater
 * @version 1.0
 * @date 2019/8/15 14:37
 * @func
 */
@RestController
@RequestMapping("/api")
public class ChestController {
    @Autowired
    private ChestService chestService;

//    @RequiresPermissions(value = {"chest:listAll"})
    @GetMapping("/findAllChestMsg")
    public ResultBean findAllChestStatus(){
//        Subject subject = SecurityUtils.getSubject();
//        // 登录了返回true
//        if (subject.isAuthenticated()) {
            return chestService.findAllChestStatus();
//        } else {
//            return new ResultBean(403, "你无权访问");
//        }

    }
}
