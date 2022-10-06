package ru.s3v3nny.urlshortener.interfaces;

public interface RedisRepoInterface {
    String getViews(String key);

    void incrementValue(String key);

    boolean containsValue(String key);

    void addValue(String key);

    void deleteValue(String key);
}
