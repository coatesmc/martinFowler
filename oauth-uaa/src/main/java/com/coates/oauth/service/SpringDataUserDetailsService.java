package com.coates.oauth.service;

import com.alibaba.fastjson.JSON;
import com.coates.oauth.dao.UserAccountInfoMapper;
import com.coates.oauth.entity.UserAccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class SpringDataUserDetailsService implements UserDetailsService {

    @Autowired
    UserAccountInfoMapper userAccountInfoMapper;

    //根据 账号查询用户信息
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        //将来连接数据库根据账号查询用户信息
        UserAccountInfo userByUsername = userAccountInfoMapper.getUserByUsername(userName);

        if (userByUsername == null) {
            //如果用户查不到，返回null，由provider来抛出异常
            return null;
        }
        //根据用户的id查询用户的权限
        List<String> permissions = userAccountInfoMapper.findPermissionsByUserId(userByUsername.getId());
        //将permissions转成数组
        String[] permissionArray = new String[permissions.size()];
        permissions.toArray(permissionArray);
        String principal = JSON.toJSONString(userByUsername);
        UserDetails userDetails = User.withUsername(principal).password(userByUsername.getPassword()).authorities(permissionArray).build();
        return userDetails;
    }
}
