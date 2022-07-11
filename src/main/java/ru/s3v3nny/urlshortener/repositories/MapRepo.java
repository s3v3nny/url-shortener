package ru.s3v3nny.urlshortener.repositories;

import ru.s3v3nny.urlshortener.services.LinkRepoInterface;

import java.util.HashMap;

public class MapRepo implements LinkRepoInterface {

    protected HashMap<String, String> map = new HashMap<>();

    @Override
    public void addNewValue(String key, String value) {
        map.put(key, value);
    }

    @Override
    public String getValue(String key) {
        if (map == null) {
            return null;
        } else {
            return map.get(key);
        }
    }

    @Override
    public void deleteValue(String key) {
        map.remove(key);
    }

    @Override
    public HashMap<String, String> getMap() {
        return map;
    }

    @Override
    public boolean containsValue(String key) {
        return map.containsKey(key);
    }
}
