package ru.s3v3nny.urlshortener.repositories;

import redis.clients.jedis.Jedis;
import ru.s3v3nny.urlshortener.interfaces.RedisRepoInterface;
import ru.s3v3nny.urlshortener.services.JedisConnectionPool;

public class RedisRepo implements RedisRepoInterface {

    @Override
    public String getViews(String key) {
        Jedis jedis = JedisConnectionPool.getInstance().lease();

        String value = jedis.get(key);
        JedisConnectionPool.getInstance().release(jedis);

        return value;
    }

    @Override
    public void incrementValue(String key) {
        Jedis jedis = JedisConnectionPool.getInstance().lease();

        jedis.incr(key);
        JedisConnectionPool.getInstance().release(jedis);
    }

    @Override
    public boolean containsValue(String key) {
        Jedis jedis = JedisConnectionPool.getInstance().lease();

        boolean result = jedis.exists(key);
        JedisConnectionPool.getInstance().release(jedis);

        return result;
    }

    @Override
    public void addValue(String key) {
        Jedis jedis = JedisConnectionPool.getInstance().lease();

        jedis.set(key, "0");
        JedisConnectionPool.getInstance().release(jedis);
    }

    @Override
    public void deleteValue(String key) {
        Jedis jedis = JedisConnectionPool.getInstance().lease();

        jedis.del(key);
        JedisConnectionPool.getInstance().release(jedis);
    }
}
