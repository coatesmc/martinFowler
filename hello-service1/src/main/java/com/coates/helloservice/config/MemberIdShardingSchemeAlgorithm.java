package com.coates.helloservice.config;

import io.shardingsphere.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * @ClassName MemberIdShardingSchemeAlgorithm
 * @Description 分库策略
 * @Author mc
 * @Date 2019/8/1 17:24
 * @Version 1.0
 **/
public class MemberIdShardingSchemeAlgorithm implements PreciseShardingAlgorithm<Integer> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
        for (String str : availableTargetNames) {
            int index = shardingValue.getValue() % 100;
            return str + (index > 49 ? "2" : "1");
        }
        return null;
    }
}
