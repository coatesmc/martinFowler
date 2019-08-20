package com.coates.registrationcenter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coates.registrationcenter.entity.LoginVO;
import com.coates.registrationcenter.entity.UserAccountInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author jobob
 * @since 2019-04-28
 */
public interface IUserAccountInfoService extends IService<UserAccountInfo> {
    /**
     * 用户登录
     * @param request
     * @param loginVO
     */
    String userLogin(HttpServletRequest request, LoginVO loginVO);

    /**
     * 查询用户列表
     * @param pageIndex
     * @param pageSize
     * @param userAccountInfo
     * @return
     */
    IPage<UserAccountInfo> queryAccoout(int pageIndex, int pageSize, UserAccountInfo userAccountInfo);

    /**
     * 添加用户
     * @param userAccountInfo
     * @return
     */
    int addAccount(UserAccountInfo userAccountInfo);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int delAccount(String id);

    /**
     * 获取账号信息
     * @param account
     * @return
     */
    boolean getAccountInfo(String account);

    /**
     * 查询单个信息
     * @param account
     * @param loginPhoneAndEmailType
     * @return
     */
    UserAccountInfo queryOneAccooutInfo(String account, int loginPhoneAndEmailType);
}
