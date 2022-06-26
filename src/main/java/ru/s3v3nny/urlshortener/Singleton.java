package ru.s3v3nny.urlshortener;

import java.util.HashMap;

public class Singleton {
    private static Singleton instance = new Singleton();

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    private static HashMap<String, String> map = new HashMap<>();

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
}
