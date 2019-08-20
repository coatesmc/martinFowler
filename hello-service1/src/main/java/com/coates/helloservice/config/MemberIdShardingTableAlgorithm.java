package com.coates.helloservice.config;

import io.shardingsphere.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * @ClassName MemberIdShardingTableAlgorithm
 * @Description 会员信息分表策略，按照 id 分表
 * @Author mc
 * @Date 2019/8/1 17:32
 * @Version 1.0
 **/
public class MemberIdShardingTableAlgorithm implements PreciseShardingAlgorithm<Integer> {
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
        int index = shardingValue.getValue() % 100;
        return shardingValue.getLogicTableName() + "_" + (index < 10 ? "1" + index : index + "");
    }
}
