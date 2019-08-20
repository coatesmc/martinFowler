package com.coates.registrationcenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-05-07
 */
public interface ISysRoleService extends IService<SysRole> {
    /**
     * 添加
     * @param sysRole
     */
    void add(SysRole sysRole);

    /**
     * 通过主键查询
     * @param id
     */
    SysRole findById(String id);

    /**
     * 通过主键查询
     * @param sysRole
     */
    SysRole findByEntity(SysRole sysRole);


    /**
     * 删除用户数据
     * @param id
     */
    void removeSysRole(String id);

    /**
     * 查询列表
     * @param pageIndex
     * @param pageSize
     * @param sysRole
     * @return
     */
    IPage<SysRole> findSysRoleList(int pageIndex, int pageSize, SysRole sysRole);
}
