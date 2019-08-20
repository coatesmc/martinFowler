package com.coates.registrationcenter.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coates.registrationcenter.service.ISysRoleService;
import com.coates.registrationcenter.service.SysRole;
import com.coates.tools.entity.ApiResult;
import com.coates.tools.entity.ApiResultPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-05-07
 */

@RestController(value = "/sys-role")
public class SysRoleController {

    private final ISysRoleService iSysRoleService;

    @Autowired
    public SysRoleController(ISysRoleService iSysRoleService) {
        this.iSysRoleService = iSysRoleService;
    }

    /**
     * 主键查询
     * @param sysRole
     * @return
     */
    @RequestMapping("/addSysRole")
    public ApiResult addSysRole(SysRole sysRole) {
        iSysRoleService.add(sysRole);
        return ApiResult.successInstance();
    }

    /**
     * 查询详细
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public ApiResult findById(String id) {
        iSysRoleService.findById(id);
        return ApiResult.successInstance();
    }

    /**
     * 查询列表
     * @param sysRole
     * @return
     */
    @RequestMapping("/findSysRoleList")
    public ApiResultPage findSysRoleList(SysRole sysRole) {
        int pageIndex = 0;
        int pageSize = 0;
        //加上自己的逻辑判断
        IPage<SysRole> page = iSysRoleService.findSysRoleList(pageIndex, pageSize, sysRole);

        return ApiResultPage.successInstance(Integer.parseInt(page.getTotal() + ""), pageIndex, pageSize).setData(page.getRecords());
    }

    /**
     * 修改
     * @param sysRole
     * @return
     */
    @RequestMapping("/modifySysRole")
    public ApiResult modifySysRole(SysRole sysRole) {
        //参数校验
        return ApiResult.successInstance();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("/removeSysRole")
    public ApiResult removeSysRole(String id) {
        iSysRoleService.removeSysRole(id);
        return ApiResult.successInstance();
    }


}
