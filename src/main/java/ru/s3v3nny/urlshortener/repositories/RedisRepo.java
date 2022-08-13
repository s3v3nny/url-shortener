package ru.s3v3nny.urlshortener.repositories;

import redis.clients.jedis.Jedis;
import ru.s3v3nny.urlshortener.interfaces.RedisRepoInterface;

public class RedisRepo implements RedisRepoInterface {

    private Jedis getRedisConnection() {
        return new Jedis("sloth-1.suslovd.ru", 9379);
    }

    @Override
    public String getViews(String key) {
        Jedis jedis = getRedisConnection();

        String value = jedis.get(key);
        jedis.close();

        return value;
    }

    @Override
    public void incrementValue(String key) {
        Jedis jedis = getRedisConnection();

        jedis.incr(key);
        jedis.close();
    }

    @Override
    public boolean containsValue(String key) {
        Jedis jedis = getRedisConnection();

        boolean result = jedis.exists(key);
        jedis.close();

        return result;
    }

    @Override
    public void addValue(String key) {
        Jedis jedis = getRedisConnection();

        jedis.set(key, "0");
        jedis.close();
    }

    @Override
    public void deleteValue(String key) {
        Jedis jedis = getRedisConnection();

        jedis.del(key);
        jedis.close();
    }
}
