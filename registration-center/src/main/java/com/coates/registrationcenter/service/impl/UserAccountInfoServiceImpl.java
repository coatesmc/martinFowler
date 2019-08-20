package com.coates.registrationcenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.coates.registrationcenter.config.MyRuntimeException;
import com.coates.registrationcenter.dao.UserAccountInfoMapper;
import com.coates.registrationcenter.entity.UserAccountInfo;
import com.coates.registrationcenter.enums.LoginType;
import com.coates.registrationcenter.service.IUserAccountInfoService;
import com.coates.registrationcenter.entity.LoginVO;
import com.coates.registrationcenter.enums.AccountStatusEnums;
import com.coates.registrationcenter.util.IpUtil;
import com.coates.tools.cache.JedisCache;
import com.coates.tools.constant.KeyConstants;
import com.coates.tools.enums.ErrorCode;
import com.coates.tools.util.MD5Util;
import com.coates.tools.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2019-04-28
 */
@Service
@Transactional
@Slf4j
public class UserAccountInfoServiceImpl extends ServiceImpl<UserAccountInfoMapper, UserAccountInfo> implements IUserAccountInfoService {
    private static final Logger logger = LoggerFactory.getLogger(UserAccountInfoServiceImpl.class);

    private final UserAccountInfoMapper userAccountInfoMapper;

    @Autowired
    public UserAccountInfoServiceImpl(UserAccountInfoMapper userAccountInfoMapper) {
        this.userAccountInfoMapper = userAccountInfoMapper;
    }

    @Override
    public String userLogin(HttpServletRequest request, LoginVO loginVO) {
        String token =null;
        try {
            //判断用户登录类型
            QueryWrapper<UserAccountInfo> queryWrapper = new QueryWrapper<>();
            if (loginVO.getType() == 1) {
                queryWrapper.eq("mobile", loginVO.getAccount());
            } else {
                queryWrapper.eq("email", loginVO.getAccount());
            }
            queryWrapper.eq("status", AccountStatusEnums.STATUS_ZERO.getCode());

            UserAccountInfo userAccountInfo = oneQueryUserAccountLogin(queryWrapper);

            String password = MD5Util.toMD5(loginVO.getPassword().trim() + userAccountInfo.getSalt());

            logger.info("database password:{},yout current password:{},", userAccountInfo.getPassword(), password);
            //封装登录数据 存入缓存
             token = TokenUtil.getToken(KeyConstants.LOGIN_TOKEN_KEY, userAccountInfo.getMobile() + "");
            //权限查询
            JedisCache.setRedisObjectExpired(token, userAccountInfo, KeyConstants.TIME);
            //保存token 和登录信息
            this.saveLogin(userAccountInfo,token,request);
        } catch (Exception e) {
            logger.error("Server exception ---> {}",e.getMessage());
            throw new MyRuntimeException(ErrorCode.UNKNOW_ERROR.getCode(),ErrorCode.UNKNOW_ERROR.getMsg());
        }
        return token;
    }

    /**
     * 更新登录用户数据
     * @param userAccountInfo
     * @param token
     * @param request
     */
    private void saveLogin(UserAccountInfo userAccountInfo, String token,  HttpServletRequest request) {
        UserAccountInfo accountInfoNew = new UserAccountInfo();
        accountInfoNew.setId(userAccountInfo.getId());
        accountInfoNew.setLastLoginIp(IpUtil.getRemortIP(request));
        accountInfoNew.setLastLoginTime(new Date());
        accountInfoNew.setToken(token);
        logger.info("this is save login info --->>{}",accountInfoNew);
        this.userAccountInfoMapper.updateById(accountInfoNew);
    }

    private UserAccountInfo oneQueryUserAccountLogin(QueryWrapper<UserAccountInfo> queryWrapper) {
        UserAccountInfo userAccountInfo = userAccountInfoMapper.selectOne(queryWrapper);
        if (userAccountInfo == null) {
            throw new MyRuntimeException(ErrorCode.USER_LOGIN_ERROR.getCode(), ErrorCode.USER_LOGIN_ERROR.getMsg());
        }
        return userAccountInfo;
    }


    @Override
    public IPage<UserAccountInfo> queryAccoout(int pageIndex, int pageSize, UserAccountInfo userAccountInfo) {
        QueryWrapper<UserAccountInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.setEntity(userAccountInfo);
        IPage<UserAccountInfo> page = new Page<>(pageIndex, pageSize);

        return super.baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public int addAccount(UserAccountInfo userAccountInfo) {
        return userAccountInfoMapper.insert(userAccountInfo);
    }



    @Override
    public int delAccount(String id) {
        return baseMapper.deleteById(id);
    }

    @Override
    public boolean getAccountInfo(String account) {
        Boolean type = false;
        QueryWrapper<UserAccountInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",account).or().eq("email",account);
        List<UserAccountInfo> infos = userAccountInfoMapper.selectList(queryWrapper);
       for (UserAccountInfo info :infos) {
           if (account.equals(info.getEmail())){
               type=true;
           }
           if (account.equals(info.getMobile())){
               type=true;
           }
       }
        return type;
    }

    @Override
    public UserAccountInfo queryOneAccooutInfo(String account, int loginPhoneAndEmailType) {
        QueryWrapper<UserAccountInfo> queryWrapper = new QueryWrapper<>();
        assemblyAccountQueryConditions(account, loginPhoneAndEmailType, queryWrapper);
        return userAccountInfoMapper.selectOne(queryWrapper);
    }

    /**
     * 组装账号查询条件
     * @param account
     * @param loginPhoneAndEmailType
     * @param queryWrapper
     */
    private void assemblyAccountQueryConditions(String account, int loginPhoneAndEmailType, QueryWrapper<UserAccountInfo> queryWrapper) {
        if (loginPhoneAndEmailType== LoginType.TYPE_MOBILE_PHONE.getCode()){
            queryWrapper.eq("mobile",account);
        } else if (loginPhoneAndEmailType== LoginType.TYPE_EMAIl.getCode()){
            queryWrapper.eq("email",account);
        }
    }
}
