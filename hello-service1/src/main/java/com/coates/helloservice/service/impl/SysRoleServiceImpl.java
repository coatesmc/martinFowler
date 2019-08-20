package com.coates.helloservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.coates.helloservice.dao.SysRoleMapper;
import com.coates.helloservice.entity.SysRole;
import com.coates.helloservice.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-05-07
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public void add(SysRole sysRole) {

        sysRoleMapper.insert(sysRole);
    }

    @Override
    public SysRole findById(String id) {
        return sysRoleMapper.selectById(id);
    }

    @Override
    public SysRole findByEntity(SysRole sysRole) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(sysRole);
        return sysRoleMapper.selectOne(queryWrapper);
    }

    @Override
    public void removeSysRole(String id) {
        sysRoleMapper.deleteById(id);
    }

    @Override
    public IPage<SysRole> findSysRoleList(int pageIndex, int pageSize, SysRole sysRole) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(sysRole);
        IPage<SysRole> page = new Page<>(pageIndex, pageSize);

        return super.baseMapper.selectPage(page, queryWrapper);
    }
}
