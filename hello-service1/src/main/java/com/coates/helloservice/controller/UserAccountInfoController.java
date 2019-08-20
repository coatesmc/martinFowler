package com.coates.helloservice.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.coates.helloservice.config.MyRuntimeException;
import com.coates.helloservice.entity.LoginVO;
import com.coates.helloservice.entity.UserAccountInfo;
import com.coates.helloservice.enums.LoginType;
import com.coates.helloservice.service.IUserAccountInfoService;
import com.coates.tools.util.*;

import com.coates.tools.entity.ApiResult;
import com.coates.tools.entity.ApiResultPage;
import com.coates.tools.enums.ErrorCode;
import com.coates.tools.enums.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-04-28
 */

@RestController("/userAccountInfo")
public class UserAccountInfoController {

    private static Logger logger = LoggerFactory.getLogger(UserAccountInfoController.class);
    private static  final int RANDOM_NUMBER_LENGTH = 6;
    @Autowired
    private final IUserAccountInfoService iUserAccountInfoService;

    public UserAccountInfoController(IUserAccountInfoService iUserAccountInfoService) {
        this.iUserAccountInfoService = iUserAccountInfoService;
    }

    /**
     * 用户登录
     *
     * @param loginVO
     * @return
     */
    @RequestMapping("/userLogin")
    public ApiResult userLogin(HttpServletRequest request, @Validated LoginVO loginVO) {
        //判断账号类型
        int type = isLoginPhoneAndEmailType(loginVO.getAccount());
        loginVO.setType(type);
        //加上自己的逻辑判断
        String token = iUserAccountInfoService.userLogin(request, loginVO);

        return ApiResult.successInstance().setData(token);
    }

    /**
     * 新增用户信息
     *
     * @param userAccountInfo
     * @return
     */
    @RequestMapping("/adduserAccountInfo")
    public ApiResult addUserAccountInfo(HttpServletRequest request, @Validated UserAccountInfo userAccountInfo, @NotBlank(message = "账号不能为空")
            String account) {
        //判断账号类型
        int type = isLoginPhoneAndEmailType(account);
        if (type == LoginType.TYPE_MOBILE_PHONE.getCode()) {
            userAccountInfo.setMobile(account);
        } else if (type == LoginType.TYPE_EMAIl.getCode()) {
            userAccountInfo.setEmail(account);
        }
        if (iUserAccountInfoService.getAccountInfo(account)) {
            return ApiResult.CustomErrorInstance(ErrorCode.ACCOUNT_EXISTED_ERROR.getCode(), ErrorCode.ACCOUNT_EXISTED_ERROR.getMsg());
        }
        userAccountInfo.setSalt(RandomUtils.generateNumberChar(RANDOM_NUMBER_LENGTH));
        userAccountInfo.setPassword(MD5Util.toMD5(userAccountInfo.getPassword() + userAccountInfo.getSalt()));
        userAccountInfo.setRegIp(IpUtil.getRemortIP(request));
        userAccountInfo.setRegTime(new Date());

        //加上自己的逻辑判断
        iUserAccountInfoService.addAccount(userAccountInfo);
        return ApiResult.successInstance();
    }

    /**
     * 修改用户信息
     *
     * @param userAccountInfo
     * @return
     */
    @RequestMapping("/updateAccountInfo")
    public ApiResult updateAccountInfo(@Validated UserAccountInfo userAccountInfo) {
        //加上自己的逻辑判断
        iUserAccountInfoService.updateById(userAccountInfo);
        return ApiResult.successInstance();
    }

    /**
     * 删除账户信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleAccountInfo")
    public ApiResult deleAccountInfo(String id) {
        //加上自己的逻辑判断
        iUserAccountInfoService.delAccount(id);
        return ApiResult.successInstance();
    }

    /**
     * @param userAccountInfo
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping("/getUserInfo")
    public ApiResultPage getUserInfo(UserAccountInfo userAccountInfo, Integer pageIndex, Integer pageSize) {
        if (StringUtils.isEmpty(pageIndex)) {
            throw new MyRuntimeException(HttpStatusCode.MISSING_PARAMETER.getCode(), "pageIndex 不能为空!");
        }

        if (StringUtils.isEmpty(pageSize)) {
            throw new MyRuntimeException(HttpStatusCode.MISSING_PARAMETER.getCode(), "pageSize 不能为空!");
        }

        //加上自己的逻辑判断
        IPage<UserAccountInfo> page = iUserAccountInfoService.queryAccoout(pageIndex, pageSize, userAccountInfo);
        logger.info("queryUserInfo-->> {}", page.getRecords());
        return ApiResultPage.successInstance(Integer.parseInt(page.getTotal() + ""), pageIndex, pageSize).setData(page.getRecords());
    }

    /**
     * 获取单个信息
     *
     * @param account
     * @return
     */
    @RequestMapping("/getOneUserInfo")
    public UserAccountInfo getOneUserInfo(String account) {
        if (StringUtils.isEmpty(account)) {
            throw new MyRuntimeException(HttpStatusCode.MISSING_PARAMETER.getCode(), "account 不能为空!");
        }
        //加上自己的逻辑判断
        UserAccountInfo userAccountInfo = iUserAccountInfoService.queryOneAccooutInfo(account,isLoginPhoneAndEmailType(account));
        return userAccountInfo;
    }


    /**
     * 判断用户登录类型
     *
     * @param account
     * @return type 0:都不匹配,1:手机号码,2:邮箱
     */
    private int isLoginPhoneAndEmailType(String account) {
        int type = LoginType.TYPE_DONTMATCH.getCode();
        if (AccountUtils.verifyMobile(account)) {
            type = LoginType.TYPE_MOBILE_PHONE.getCode();
        } else if (AccountUtils.verifyEmail(account)) {
            type = LoginType.TYPE_EMAIl.getCode();
        }
        if (type == LoginType.TYPE_DONTMATCH.getCode()) {
            throw new MyRuntimeException(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        return type;
    }


}
