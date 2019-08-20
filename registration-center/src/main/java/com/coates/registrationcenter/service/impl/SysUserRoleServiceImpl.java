package com.coates.registrationcenter.service.impl;

import com.coates.registrationcenter.service.ISysUserRoleService;
import com.coates.registrationcenter.service.SysRole;
import com.coates.registrationcenter.entity.SysUserRole;
import com.coates.registrationcenter.dao.SysUserRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-05-07
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public SysRole getUserRoleInfo(String id) {
           return sysUserRoleMapper.getUserRoleInfo(id);
    }
}
