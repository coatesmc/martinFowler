package com.coates.registrationcenter.service;

import com.coates.registrationcenter.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-05-07
 */
public interface ISysMenuService extends IService<SysMenu> {
    /**
     * 查询必填参数
     * @param id
     */
    List<SysMenu> getListMenu(String id);
}
