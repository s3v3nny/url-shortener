package ru.s3v3nny.urlshortener.services;

import lombok.SneakyThrows;
import redis.clients.jedis.Jedis;

import java.util.concurrent.LinkedBlockingQueue;

public class JedisConnectionPool {

    private static JedisConnectionPool instance;
    LinkedBlockingQueue<Jedis> pool = new LinkedBlockingQueue<>();

    @SneakyThrows
    public static JedisConnectionPool getInstance() {
        if (instance == null) {
            instance = new JedisConnectionPool();
        }
        return instance;
    }

    private Jedis getRedisConnection () {
        return new Jedis("sloth-1.suslovd.ru", 9379);
    }

    @SneakyThrows
    public JedisConnectionPool() {
        pool.put(getRedisConnection());
        pool.put(getRedisConnection());
        pool.put(getRedisConnection());
    }

    @SneakyThrows
    public Jedis lease() {
        return pool.take();
    }

    @SneakyThrows
    public void release(Jedis jedis) {
        pool.put(jedis);
    }
}
