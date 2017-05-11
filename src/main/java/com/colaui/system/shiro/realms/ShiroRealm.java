package com.colaui.system.shiro.realms;

import com.colaui.system.model.ColaUser;
import com.colaui.system.service.ColaUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by carl.li on 2017/5/11.
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private ColaUserService colaUserService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        System.out.println("[FirstRealm] doGetAuthenticationInfo");

        //1. 把 AuthenticationToken 转换为 UsernamePasswordToken
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;

        //2. 从 UsernamePasswordToken 中来获取 username
        String username = upToken.getUsername();

        //3. 调用数据库的方法, 从数据库中查询 username 对应的用户记录
        ColaUser colaUser = colaUserService.find(username);
        System.out.println("从数据库中获取 username: " + colaUser.getUsername() + " 所对应的用户信息.");

        //4. 若用户不存在, 则可以抛出 UnknownAccountException 异常
        if (null == colaUser || !StringUtils.hasText(colaUser.getUsername())) {
            throw new UnknownAccountException("用户不存在!");
        }

        //5. 根据用户信息的情况, 决定是否需要抛出其他的 AuthenticationException 异常.
        if (null != colaUser && !colaUser.isEnabled()) {
            throw new LockedAccountException("用户被锁定");
        }

        //6. 根据用户的情况, 来构建 AuthenticationInfo 对象并返回. 通常使用的实现类为: SimpleAuthenticationInfo
        //以下信息是从数据库中获取的.
        //1). principal: 认证的实体信息. 可以是 username, 也可以是数据表对应的用户的实体类对象.
        Object principal = username;
        //2). credentials: 密码.
        Object credentials = colaUser.getPassword(); //"fc1709d0a95a6be30bc5926fdb7f22f4";

        //3). realmName: 当前 realm 对象的 name. 调用父类的 getName() 方法即可
        String realmName = getName();
        //4). 盐值, 随机码.
        ByteSource credentialsSalt = ByteSource.Util.bytes(colaUser.getSalt());

        SimpleAuthenticationInfo info = null; //new SimpleAuthenticationInfo(principal, credentials, realmName);
        info = new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
        return info;
    }

    public static void main(String[] args) {
        String hashAlgorithmName = "MD5";
        Object credentials = "testuser";
        Object salt = ByteSource.Util.bytes("1213");
        int hashIterations = 1024;

        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        System.out.println(result);
    }

    //用于授权, 授权会被 shiro 回调的方法.
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        //1. 从 PrincipalCollection 中来获取登录用户的信息
        Object principal = principals.getPrimaryPrincipal();

        //2. 利用登录的用户的信息来获取当前用户的角色或权限(可能需要查询数据库)
        Set<String> roles = new HashSet<>();

        if ("admin".equals(principal)) {
            roles.add("admin");
        } else if ("test".equals(principal)) {
            roles.add("test");
        } else if ("dev".equals(principal)) {
            roles.add("dev");
        }

        //3. 创建 SimpleAuthorizationInfo, 并设置其 reles 属性.
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);

        //4. 返回 SimpleAuthorizationInfo 对象.
        return info;
    }
}
