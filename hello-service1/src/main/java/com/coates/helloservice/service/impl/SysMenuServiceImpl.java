package com.coates.helloservice.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coates.helloservice.config.MyRuntimeException;
import com.coates.helloservice.dao.SysMenuMapper;
import com.coates.helloservice.entity.SysMenu;
import com.coates.helloservice.entity.SysRole;
import com.coates.helloservice.service.ISysMenuService;
import com.coates.helloservice.service.ISysUserRoleService;
import com.coates.tools.constant.SystemLiteralConstants;
import com.coates.tools.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-05-07
 */
@Service
@Slf4j
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;
    private final ISysUserRoleService iSysUserRoleService;
    @Autowired
    public SysMenuServiceImpl( ISysUserRoleService iSysUserRoleService) {
        this.iSysUserRoleService = iSysUserRoleService;
    }



    @Override
    public List<SysMenu> getListMenu(String id) {
       SysRole sysRole = iSysUserRoleService.getUserRoleInfo(id);
        if (sysRole == null) {
            throw new MyRuntimeException(ErrorCode.NO_PERMISSION.getCode(),ErrorCode.NO_PERMISSION.getMsg());
        }
        if (SystemLiteralConstants.SUPER_ADMIN_USER.equals(sysRole.getRoleName())){

        }
        return null;
    }
}
