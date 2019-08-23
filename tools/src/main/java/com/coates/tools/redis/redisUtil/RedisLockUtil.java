package com.coates.tools.redis.redisUtil;

import com.goodsogood.ows.common.Config;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Collections;

/**
 * 官网推荐使用lua脚本实现redis分布式锁
 *
 * @author majie
 */
@Slf4j
public class RedisLockUtil {


    private static final Long SUCCESS = 1L;

    // 加锁脚本
    private static final String SCRIPT_LOCK = "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then redis.call('pexpire', KEYS[1], ARGV[2]) return 1 else return 0 end";
    // 解锁脚本
    private static final String SCRIPT_UNLOCK = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    // 加锁脚本sha1值
    private static final String SCRIPT_LOCK_SHA1 = Sha1Util.encrypt(SCRIPT_LOCK);
    // 解锁脚本sha1值
    private static final String SCRIPT_UNLOCK_SHA1 = Sha1Util.encrypt(SCRIPT_UNLOCK);

    /**
     * 尝试获取分布式锁
     *
     * @param redisTemplate          Redis客户端
     * @param lockKey                锁
     * @param requestId              请求标识
     * @param expireTimeMilliseconds 超期时间，多少毫秒后这把锁自动释放
     * @return 返回true表示拿到锁
     */
    @SuppressWarnings("unchecked")
    public static boolean tryGetDistributedLock(@SuppressWarnings("rawtypes") final RedisTemplate redisTemplate,
                                                final String lockKey, final String requestId, final int expireTimeMilliseconds) {
        //要使用StringRedisSerializer序列化,不然会报错
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        Object result = redisTemplate.execute(new RedisScript<Long>() {
                                                  @Override
                                                  public String getSha1( ) {
                                                      return SCRIPT_LOCK_SHA1;
                                                  }

                                                  @Override
                                                  public Class<Long> getResultType( ) {
                                                      return Long.class;
                                                  }

                                                  @Override
                                                  public String getScriptAsString( ) {
                                                      return SCRIPT_LOCK;
                                                  }

                                              }, Collections.singletonList(lockKey),// KEYS[1]
                requestId, // ARGV[1]
                String.valueOf(expireTimeMilliseconds) // ARGV[2]
        );

        return SUCCESS.equals(result);
    }

    /**
     * 释放分布式锁
     *
     * @param redisTemplate Redis客户端
     * @param lockKey       锁
     * @param requestId     请求标识
     * @return 返回true表示释放锁成功
     */
    @SuppressWarnings("unchecked")
    public static boolean releaseDistributedLock(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate,
                                                 String lockKey, String requestId) {
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        Object result = redisTemplate.execute(new RedisScript<Long>() {
            @Override
            public String getSha1( ) {
                return SCRIPT_UNLOCK_SHA1;
            }

            @Override
            public Class<Long> getResultType( ) {
                return Long.class;
            }

            @Override
            public String getScriptAsString( ) {
                return SCRIPT_UNLOCK;
            }

        }, Collections.singletonList(lockKey), requestId);

        return SUCCESS.equals(result);
    }


    /**
     * 尝试重新获取锁，直到锁的时间完毕
     *
     * @param redisTemplate
     * @param lockKey
     * @param requestId
     * @param expireTimeMilliseconds
     * @return
     */
    public static boolean tryGetLock(final RedisTemplate redisTemplate,
                              final String lockKey, final String requestId, final int expireTimeMilliseconds) {
        try {
            for (int i = 0; i <= expireTimeMilliseconds; i++) {
                log.debug("正在尝试重新获取锁：lockKey = {}, uuid = {}", lockKey, requestId);
                boolean lock = RedisLockUtil.tryGetDistributedLock(redisTemplate, lockKey, requestId, Config.USER_CONSUME_LOCK_EXPIRE);
                if (lock) {
                    log.debug("获取到锁：lockKey = {}, uuid = {}", lockKey, requestId);
                    return true;
                }
                Thread.sleep(Config.TRY_GET_LOCK_INTERVAL);
            }
        } catch (Exception e) {
            log.error("尝试重新获取锁出错，lockKey = {}, uuid = {}", lockKey, requestId);
            log.error("尝试重新获取锁出错", e);
        }
        return false;
    }

}