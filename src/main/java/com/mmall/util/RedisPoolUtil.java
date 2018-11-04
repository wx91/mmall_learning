package com.mmall.util;

import com.mmall.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Slf4j
public class RedisPoolUtil {
    /**
     * 设置key的有效期，单位是秒
     *
     * @param key
     * @param exTime
     * @return
     */
    public static Long expire(String key, int exTime) {
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.expire(key, exTime);
            RedisPool.returnResource(jedis);
            return result;
        } catch (Exception e) {
            log.error("expire key:{} error {}", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
    }

    /**
     * @param key
     * @param value
     * @param exTime 单位时秒
     * @return
     */
    public static String setEx(String key, String value, int exTime) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key, exTime, value);
            RedisPool.returnResource(jedis);
            return result;
        } catch (Exception e) {
            log.error("setex key:{} value:{} error {}", key, value, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
    }

    public static String set(String key, String value) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key, value);
            RedisPool.returnResource(jedis);
            return result;
        } catch (Exception e) {
            log.error("set key:{} value:{} error {}", key, value, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
    }

    public static String get(String key) {
        String result = null;
        Jedis jedis = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
            RedisPool.returnResource(jedis);
            return result;
        } catch (Exception e) {
            log.error("get key:{}  error {}", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
    }

    public static Long del(String key) {
        Long result = null;
        Jedis jedis = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
            RedisPool.returnResource(jedis);
            return result;
        } catch (Exception e) {
            log.error("get key:{}  error {}", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
    }

    public static void main(String[] args) {
        Jedis jedis = RedisPool.getJedis();
        RedisPoolUtil.set("keyTest", "value");
        String value = RedisPoolUtil.get("keyTest");
        RedisPoolUtil.setEx("keyex", "valueEx", 60 * 10);
        RedisPoolUtil.expire("keyTest",60*20);
        RedisPoolUtil.del("keyTest");
        System.out.println("end");
    }

}
