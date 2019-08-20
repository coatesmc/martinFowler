package com.coates.tools.cache;


import com.coates.tools.util.SerializeUtil;
import com.coates.tools.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;
import java.util.TreeSet;


/**
 * Created by huangyp on 2017/9/6.
 */
public class JedisCache {

    //redis 连接池
    private static JedisPool jedisPool = null;

    private static final Logger logger = LoggerFactory.getLogger(JedisCache.class);

    static {
        JedisConfig config = new JedisConfig();
        //SpringUtil.getBean(.class);
        // JedisConfig config = new ;
        config.setAddress("127.0.0.1");
        if (config == null) {
            config = new JedisConfig();
            config.setAddress("127.0.0.1");
            config.setAuth("");
        }
        jedisPool = new JedisPool(config, config.getAddress(), config.getPort(), config.getTimeout(), config.getAuth());
        logger.info(String.format("init jedis pool was %s!", (jedisPool == null ? "fail" : "success")));
    }


    /**
     * 缓存一个对象
     *
     * @param key
     * @param val
     */
    public static void setRedisObject(String key, Object val) {
        if (StringUtils.isEmpty(key) || val == null) {//如果key==null 就不保存了
            return;
        }
        Jedis jedis = JedisCache.getJedis();
        try {
            jedis.set(key.getBytes(), SerializeUtil.serialize(val));
        } catch (Exception e) {
            logger.error(String.format("setRedisObject for key [%s] fail : ", key), e);
        } finally {
            closeResource(jedis);
        }
    }

    public static Double zincrby(String key, double score, String member) {
        Jedis jedis = JedisCache.getJedis();
        try {
            return jedis.zincrby(key, score, member);
        } catch (Exception e) {
            logger.error(String.format("set zincrby for key [%s] fail : ", key), e);
        } finally {
            closeResource(jedis);
        }
        return 0.0;
    }

    public static Set<String> zrevrange(String key, Long start, Long fetchSize) {
        Jedis jedis = JedisCache.getJedis();
        try {
            return jedis.zrevrange(key, start, fetchSize);
        } catch (Exception e) {
            logger.error(String.format("get zrevrange for key [%s] fail : ", key), e);
        } finally {
            closeResource(jedis);
        }
        return new TreeSet<>();
    }

    /**
     * 根据缓存key取出一个对象
     *
     * @param key
     * @return
     */
    public static Object getRedisObject(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = JedisCache.getJedis();
        try {
            byte[] bs = jedis.get(key.getBytes());
            if (bs != null) {
                Object object = SerializeUtil.unserialize(bs);
                return object;
            }
        } catch (Exception e) {
            logger.error(String.format("getRedisObject for key [%s] fail : ", key), e);
        } finally {
            closeResource(jedis);
        }
        return null;
    }

    /**
     * @param key
     * @param field
     * @param value
     * @return
     */
    public static Long hset(String key, String field, String value) {
        Jedis jedis = JedisCache.getJedis();
        Long result = null;
        try {
            result = jedis.hset(key, field, value);
        } catch (Exception e) {
            logger.error(String.format("getRedisObject for key [%s] fail : ", key), e);
        } finally {
            closeResource(jedis);
        }
        return result;
    }

    /**
     * @param key
     * @param field
     * @param value
     * @return
     */
    public static Object hget(String key, String field, String value) {
        Jedis jedis = JedisCache.getJedis();
        Object result = null;
        try {
            result = jedis.hget(key, field);
        } catch (Exception e) {
            logger.error(String.format("getRedisObject for key [%s] fail : ", key), e);
        } finally {
            closeResource(jedis);
        }
        return result;
    }

    /**
     * 缓存一个对象 并设置缓存时间
     *
     * @param key
     * @param val
     * @param expiredSeconds
     */
    public static void setRedisObjectExpired(String key, Object val, int expiredSeconds) {
        if (StringUtils.isEmpty(key) || val == null) {
            return;
        }
        Jedis jedis = getJedis();
        try {
            jedis.set(key.getBytes(), SerializeUtil.serialize(val));
            jedis.expire(key.getBytes(), expiredSeconds);
        } catch (Exception e) {
            logger.error(String.format("setRedisObjectExpired for key [%s] fail : ", key), e);
        } finally {
            closeResource(jedis);

        }

    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            }
        } catch (Exception e) {
            logger.error("getJedis for key fail : ", e);
        }
        return null;
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void closeResource(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 删除对象
     *
     * @param key
     * @return
     */
    public static boolean deleteObject(String key) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        Jedis jedis = getJedis();
        try {
            Long count = jedis.del(key);
            return (count != null && count > 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
        return false;
    }

    //设置系列化对象，以及过期时间
    public static String setex(String key, String serializeValue, int expiredSeconds) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = getJedis();
        try {
            return jedis.setex(key, expiredSeconds, serializeValue);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
        return null;
    }

    //设置系列化对象
    public static String set(String key, String serializeValue) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = getJedis();
        try {
            return jedis.set(key, serializeValue);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
        return null;
    }

    //获取系列化对象
    public static String get(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Jedis jedis = getJedis();
        try {
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
        return null;
    }

    //设置过期时间
    public static void expire(String key, int expiredSeconds) {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        Jedis jedis = getJedis();
        try {
            jedis.expire(key, expiredSeconds);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }

    }

}
