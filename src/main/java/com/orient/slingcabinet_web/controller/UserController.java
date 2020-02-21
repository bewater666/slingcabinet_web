package com.orient.slingcabinet_web.controller;

import com.orient.slingcabinet_web.dao.UserMapper;
import com.orient.slingcabinet_web.model.Constant;
import com.orient.slingcabinet_web.model.ResultBean;
import com.orient.slingcabinet_web.model.User;
import com.orient.slingcabinet_web.utils.exception.CustomUnauthorizedException;
import com.orient.slingcabinet_web.utils.jwt.JwtUtil;
import com.orient.slingcabinet_web.utils.password.AesCipherUtil;
import com.orient.slingcabinet_web.utils.redis.JedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zhoudun
 * @Date: 2019/3/14 16:39
 * @Func:
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
@PropertySource("classpath:config.properties")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    /**
     * RefreshToken过期时间
     */
    @Value("${refreshTokenExpireTime}")
    private String refreshTokenExpireTime;

    @PostMapping("login")
    public ResultBean login(@RequestBody User user){
        User user1 = userMapper.selectOne(user.getAccount());
        if (user1 == null) {
            return new ResultBean(201,"该账号不存在");

        }
        // 密码进行AES解密
        String key = AesCipherUtil.deCrypto(user1.getPassword());
        System.out.println(user.getAccount()+"用户解密后的密码是"+key);
        // 因为密码加密是以帐号+密码的形式进行加密的，所以解密后的对比是帐号+密码
        if (key.equals(user.getAccount() + user.getPassword())) {
            // 清除可能存在的Shiro权限信息缓存
            if (JedisUtil.exists(Constant.PREFIX_SHIRO_CACHE + user.getAccount())) {
                JedisUtil.delKey(Constant.PREFIX_SHIRO_CACHE + user.getAccount());
            }
            // 设置RefreshToken，时间戳为当前时间戳，直接设置即可(不用先删后设，会覆盖已有的RefreshToken)
            String currentTimeMillis = String.valueOf(System.currentTimeMillis());
            JedisUtil.setObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + user.getAccount(), currentTimeMillis, Integer.parseInt(refreshTokenExpireTime));
            // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
            String token = JwtUtil.sign(user.getAccount(), currentTimeMillis);
//            httpServletResponse.setHeader("Authorization", token);
//            httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
            return new ResultBean(HttpStatus.OK.value(), "登录成功(Login Success.)", token);
        } else {
            throw new CustomUnauthorizedException("帐号或密码错误(Account or Password Error.)");
        }

    }



    @GetMapping("/testLogin")
    public ResultBean article() {
        Subject subject = SecurityUtils.getSubject();
        // 登录了返回true
        if (subject.isAuthenticated()) {
            return new ResultBean(HttpStatus.OK.value(), "您已经登录了(You are already logged in)", null);
        } else {
            return new ResultBean(HttpStatus.OK.value(), "你是游客(You are guest)", null);
        }
    }


}
