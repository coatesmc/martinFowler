package com.coates.registrationcenter.dao;

import com.coates.registrationcenter.service.SysRole;
import com.coates.registrationcenter.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-05-07
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    /**
     * 通过用户id获取用户角色
     * @param id
     */
    @Select("select r.* from sys_user_role u LEFT JOIN sys_role r on u.roleId= r.id where u.userId=#{id}")
    SysRole getUserRoleInfo(@Param("id") String id);
}
