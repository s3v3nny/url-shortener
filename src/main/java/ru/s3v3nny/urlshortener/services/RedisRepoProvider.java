package ru.s3v3nny.urlshortener.services;

import ru.s3v3nny.urlshortener.interfaces.RedisRepoInterface;
import ru.s3v3nny.urlshortener.repositories.RedisRepo;

public class RedisRepoProvider {

    private static RedisRepoInterface redisRepo;

    public static RedisRepoInterface getRedisRepo() {
        if(redisRepo == null){
            redisRepo = new RedisRepo();
        }
        return redisRepo;
    }

}
