package ru.s3v3nny.urlshortener.repositories;

import ru.s3v3nny.urlshortener.interfaces.LinkRepoInterface;

import java.util.HashMap;
import java.util.Map;

public class MapRepo implements LinkRepoInterface {

    private HashMap<String, String> map = new HashMap<>();

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
    public String getValues() {
        String result = "";

        for (Map.Entry<String, String> entry : getMap().entrySet()) {
            result += entry.getKey() + " equals "+ entry.getValue() + "\n";
        }

        return result.toString();
    }

    private HashMap<String, String> getMap() {
        return map;
    }

    @Override
    public boolean containsValue(String key) {
        return map.containsKey(key);
    }
}
