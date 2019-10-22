package com.coates.tools.cache;


import com.coates.tools.util.SpringUtil;
import com.coates.tools.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by huangyp on 2017/9/6.
 */
public class JedisCacheNoSerialize {

    //redis 连接池
    private static JedisPool jedisPool = null;

    private static final Logger logger = LoggerFactory.getLogger(JedisCacheNoSerialize.class);

    static {
        JedisConfig config = SpringUtil.getBean(JedisConfig.class);
        jedisPool = new JedisPool(config, config.getAddress(), config.getPort(), config.getTimeout(), config.getAuth());
        logger.info(String.format( "init jedis pool was %s!",(jedisPool == null ? "fail" : "success")) );
    }


    /**
     * 缓存一个对象
     * @param key
     * @param val
     */
    public static void setRedisObject(String key, String val){
        if(StringUtils.isEmpty(key) || val == null){//如果key==null 就不保存了
            return ;
        }
        Jedis jedis = JedisCacheNoSerialize.getJedis();
        try {
            jedis.set(key,val);
        } catch (Exception e) {
            logger.error(String.format("setRedisObject for key [%s] fail : ",key),e);
        }finally{
            closeResource(jedis);
        }
    }


    /**
     * 根据缓存key取出一个对象
     * @param key
     * @return
     */
    public static Object getRedisObject(String key){
        if ( StringUtils.isEmpty(key) ) {
            return null;
        }
        Jedis jedis = JedisCacheNoSerialize.getJedis();
        try {
            String s = jedis.get(key);
            return s;
        } catch (Exception e) {
            logger.error(String.format("getRedisObject for key [%s] fail : ",key),e);
        }finally{
            closeResource(jedis);
        }
        return null;
    }

    /**
     * 缓存一个对象 并设置缓存时间
     * @param key
     * @param val
     * @param expiredSeconds
     */
    public static void setRedisObjectExpired(String key, String val, int expiredSeconds){
        if(StringUtils.isEmpty(key) || val == null){
            return ;
        }
        Jedis jedis = getJedis();
        try {
            jedis.set(key, val);
            jedis.expire(key,expiredSeconds);
        } catch (Exception e) {
            logger.error(String.format("setRedisObjectExpired for key [%s] fail : ",key),e);
        }finally{
            closeResource(jedis);

        }

    }

    /**
     * 获取Jedis实例
     * @return
     */
    public synchronized static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            }
        } catch (Exception e) {
            logger.error("getJedis for key fail : " ,e);
        }
        return null;
    }

    /**
     * 释放jedis资源
     * @param jedis
     */
    public static void closeResource(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 删除对象
     * @param key
     * @return
     */
    public static boolean deleteObject(String key){
        if(StringUtils.isEmpty(key) ) {
            return false;
        }
        Jedis jedis = getJedis();
        try {
            Long count = getJedis().del(key);
            return (count != null && count > 0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResource(jedis);
        }
        return false;
    }

}
