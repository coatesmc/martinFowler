package com.coates.helloservice.service.impl;

import com.coates.helloservice.common.Config;
import com.coates.helloservice.service.TestService;
import com.coates.tools.cache.JedisCache;
import com.coates.tools.cache.RedisTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @ClassName TestServiceImpl
 * @Description TODO
 * @Author mc
 * @Date 2019/8/23 10:54
 * @Version 1.0
 **/
@SuppressWarnings("ALL")
@Service("testService")
@Slf4j
public class TestServiceImpl implements TestService {

    @Transactional
    public int test(int i) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String lockKey = "";
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        log.debug("消费计时：拿锁之前：uuid = {}, time = {}", uuid, stopWatch.toString());
        //redis锁，锁当前用户的对象
        lockKey = Config.USER_CONSUME_LOCK_PREFIX + i;
        try {
            boolean lock = RedisTool.tryGetDistributedLock(lockKey, uuid, Config.USER_CONSUME_LOCK_EXPIRE);
            log.debug("是否拿到锁： key = {} uuid = {}, lock = {}", lockKey, uuid, lock);
            if (!lock) {
                lock = RedisTool.tryGetLock(lockKey, uuid, Config.USER_CONSUME_LOCK_EXPIRE + 1000);
            }
            log.debug("消费计时：拿到锁：uuid = {}, time = {}", uuid, stopWatch.toString());

            if (lock) {
                log.debug("执行锁的内容: key = {}, uuid = {}", lockKey, uuid);

                Thread.sleep(1000);
                log.info("int : 【{}】", i);
            }
        } catch (InterruptedException e) {
            log.error("异常", e);
            // 配合Transactional手动让spring回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        } finally {
            log.debug("消费执行完毕: key = {}, uuid = {}", lockKey, uuid);
            if (!StringUtils.isEmpty(lockKey) && !StringUtils.isEmpty(uuid)) {
                log.debug("消费执行完毕，释放锁: key = {}, uuid = {}", lockKey, uuid);
                RedisTool.releaseDistributedLock(lockKey, uuid);
            }
            stopWatch.stop();
        }
        return 0;
    }
}
