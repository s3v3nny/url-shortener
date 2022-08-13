package ru.s3v3nny.urlshortener.services;

import ru.s3v3nny.urlshortener.interfaces.LinkRepoInterface;
import ru.s3v3nny.urlshortener.interfaces.RedisRepoInterface;
import ru.s3v3nny.urlshortener.models.ShortenedLink;

import java.sql.SQLException;
import java.util.ArrayList;

public class RedisMigration {
    private static final RedisRepoInterface redisRepo = RedisRepoProvider.getRedisRepo();
    private static final LinkRepoInterface linkRepo = LinkRepoProvider.getLinkRepo();

    public static void setNullFields() throws SQLException {
        ArrayList<ShortenedLink> links = linkRepo.getValues();

        for(ShortenedLink s : links) {
            if(!(redisRepo.containsValue(s.getKey()))) {
                redisRepo.addValue(s.getKey());
            }
        }
    }
}
