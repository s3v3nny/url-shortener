package ru.s3v3nny.urlshortener.services;

import java.util.HashMap;

public class MapUtils {
    private static MapUtils instance = new MapUtils();

    private MapUtils() {
    }

    public static MapUtils getInstance() {
        if (instance == null) {
            instance = new MapUtils();
        }
        return instance;
    }

    protected HashMap<String, String> map = new HashMap<>();

    public void addNewValue(String key, String value) {
        map.put(key, value);
    }

    public String getValue(String key) {
        if (map == null) {
            return null;
        } else {
            return map.get(key);
        }
    }

    public void deleteValue(String key) {
        map.remove(key);
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public boolean containsValue (String key) {
        return map.containsKey(key);
    }
}
