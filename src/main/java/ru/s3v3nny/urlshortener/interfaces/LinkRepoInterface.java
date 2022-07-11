package ru.s3v3nny.urlshortener.interfaces;

import java.util.HashMap;

public interface LinkRepoInterface {
    void addNewValue(String key, String value);

    String getValue(String key);

    void deleteValue(String key);

    HashMap<String, String> getMap();

    boolean containsValue(String key);
}
