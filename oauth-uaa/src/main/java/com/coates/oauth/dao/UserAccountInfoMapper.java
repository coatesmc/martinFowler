package com.coates.oauth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.coates.oauth.entity.UserAccountInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-04-29
 */
@Mapper
public interface UserAccountInfoMapper extends BaseMapper<UserAccountInfo> {

    /**
     * 查询用户是否存在
     * @param userName
     * @return
     */
//    @Select("select * from user_account_info where user_name = #{userName}")
    @Select("select id, user_name as userName,password,email,mobile from user_account_info where user_name = #{userName}")
    UserAccountInfo getUserByUsername(@Param("userName") String userName);

    /**
     * 查询用户角色
     * @param userId
     * @return
     */
    @Select("select roleName from sys_role where id in (select roleId from sys_user_role where userId = #{userId})")
    List<String> findPermissionsByUserId(@Param("userId") Integer userId);
}
