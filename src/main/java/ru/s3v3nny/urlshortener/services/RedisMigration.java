package ru.s3v3nny.urlshortener.services;

import ru.s3v3nny.urlshortener.interfaces.LinkRepoInterface;
import ru.s3v3nny.urlshortener.interfaces.RedisRepoInterface;

import java.sql.SQLException;
import java.util.ArrayList;

public class RedisMigration {
    private static final RedisRepoInterface redisRepo = RedisRepoProvider.getRedisRepo();
    private static final LinkRepoInterface linkRepo = LinkRepoProvider.getLinkRepo();

    public static void setNullFields() throws SQLException {
        ArrayList<String> links = linkRepo.getValues();

        for(String s : links) {
            String key = s.substring(0,8);
            if(!(redisRepo.containsValue(key))) {
                redisRepo.addValue(key);
            }
        }
    }
}
