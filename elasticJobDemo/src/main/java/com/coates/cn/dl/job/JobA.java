package com.coates.cn.dl.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName JobA
 * @Description TODO
 * @Author mc
 * @Date 2019/5/14 9:53
 * @Version 1.0
 **/
public class JobA implements SimpleJob {
    private static Logger logger = LoggerFactory.getLogger(JobA.class);

    @Override
    public void execute(ShardingContext context) {
        logger.info("jobA>>>>>>>>>>>>");
        System.out.println("jobA>>>>>>>>>>>>");
        switch (context.getShardingItem()) {
            case 0:
                // do something by sharding item 0
                break;
            case 1:
                // do something by sharding item 1
                break;
            case 2:
                // do something by sharding item 2
                break;
            // case n: ...
        }
    }
}
