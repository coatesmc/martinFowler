package com.coates.helloservice.service;

import com.coates.helloservice.entity.SysRole;
import com.coates.helloservice.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户角色关联表 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-05-07
 */
public interface ISysUserRoleService extends IService<SysUserRole> {
    /**
     * 获取用户角色
     * @param id
     * @return
     */
    SysRole getUserRoleInfo(String id);
}
