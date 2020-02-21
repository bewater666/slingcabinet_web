package com.orient.slingcabinet_web.utils.realm;

import com.orient.slingcabinet_web.dao.PermissionMapper;
import com.orient.slingcabinet_web.dao.RoleMapper;
import com.orient.slingcabinet_web.dao.UserMapper;
import com.orient.slingcabinet_web.model.Constant;
import com.orient.slingcabinet_web.model.Permission;
import com.orient.slingcabinet_web.model.Role;
import com.orient.slingcabinet_web.model.User;
import com.orient.slingcabinet_web.utils.common.StringUtil;
import com.orient.slingcabinet_web.utils.jwt.JwtToken;
import com.orient.slingcabinet_web.utils.jwt.JwtUtil;
import com.orient.slingcabinet_web.utils.redis.JedisUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zhoudun
 * @Date: 2019/3/14 14:37
 * @Func: 自定义realm
 * @Version 1.0
 */
@Service
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;


    /**
     * 重新supports方法  不然shiro会报错
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        //instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例
        return token instanceof JwtToken;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        // 解密获得account，用于和数据库进行对比
        String account = JwtUtil.getClaim(token, Constant.ACCOUNT);
        // 帐号为空
        if (StringUtil.isBlank(account)) {
            throw new AuthenticationException("Token中帐号为空(The account in Token is empty.)");
        }
        // 查询用户是否存在
        User user = userMapper.selectOne(account);
        if (user == null) {
            throw new AuthenticationException("该帐号不存在(The account does not exist.)");
        }
        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        if (JwtUtil.verify(token) && JedisUtil.exists(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
            // 获取RefreshToken的时间戳
            String currentTimeMillisRedis = JedisUtil.getObject(Constant.PREFIX_SHIRO_REFRESH_TOKEN + account).toString();
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (JwtUtil.getClaim(token, Constant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                return new SimpleAuthenticationInfo(token, token, "userRealm");
            }
        }
        throw new AuthenticationException("Token已过期(Token expired or incorrect.)");
    }


    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        String account = JwtUtil.getClaim(principals.toString(), Constant.ACCOUNT);
        User user = new User();
        user.setAccount(account);
        // 查询用户角色
        List<Role> roles = roleMapper.findRolesByUser(user);
        for (int i = 0, roleLen = roles.size(); i < roleLen; i++) {
            Role role = roles.get(i);
            // 添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            // 根据用户角色查询权限
            List<Permission> permissions = permissionMapper.findPermissionByRole(role);
            for (int j = 0, perLen = permissions.size(); j < perLen; j++) {
                Permission permission = permissions.get(j);
                // 添加权限
                simpleAuthorizationInfo.addStringPermission(permission.getPerm_code());
            }
        }
        return simpleAuthorizationInfo;
    }


}
