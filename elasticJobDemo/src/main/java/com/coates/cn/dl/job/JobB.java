package com.coates.cn.dl.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName JobB
 * @Description TODO
 * @Author mc
 * @Date 2019/5/14 10:15
 * @Version 1.0
 **/
public class JobB implements SimpleJob {
    private static Logger logger = LoggerFactory.getLogger(JobB.class);

    @Override
    public void execute(ShardingContext shardingContext) {
     /*   logger.info("JobB>>>>getJobName>>>", shardingContext.getJobName());
        logger.info("JobB>>>>getJobName>>>", shardingContext.getJobName());
        logger.info("JobB>>>getShardingParameter>>>>", shardingContext.getShardingParameter());
        logger.info("JobB>>>>getTaskId>>>", shardingContext.getTaskId());
        logger.info("JobB>>>>getShardingItem>>>", shardingContext.getShardingItem());
        logger.info("JobB>>>>getShardingTotalCount>>>", shardingContext.getShardingTotalCount());*/

        System.out.println("JobB>>>>getJobName>>>" + shardingContext.getJobName());
        System.out.println("JobB>>>getShardingParameter>>>>" + shardingContext.getShardingParameter());
        System.out.println("JobB>>>>getTaskId>>>" + shardingContext.getTaskId());
        System.out.println("JobB>>>>getShardingItem>>>" + shardingContext.getShardingItem());
        System.out.println("JobB>>>>getShardingTotalCount>>>" + shardingContext.getShardingTotalCount());
    }



}
