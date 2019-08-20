package com.coates.registrationcenter.dao;

import com.coates.registrationcenter.entity.UserAccountInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-04-29
 */
@Mapper
public interface UserAccountInfoMapper extends BaseMapper<UserAccountInfo> {

}
