package com.orient.slingcabinet_web.utils.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author: zhoudun
 * @Date: 2019/3/14 14:42
 * @Func:
 * @Version 1.0
 */

public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
