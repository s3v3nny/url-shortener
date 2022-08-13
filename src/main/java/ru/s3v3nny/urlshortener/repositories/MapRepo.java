package ru.s3v3nny.urlshortener.repositories;

import ru.s3v3nny.urlshortener.interfaces.LinkRepoInterface;
import ru.s3v3nny.urlshortener.models.ShortenedLink;

import java.util.ArrayList;
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
    public ArrayList<ShortenedLink> getValues() {
        ArrayList<ShortenedLink> links = new ArrayList<>();

        for (Map.Entry<String, String> entry : getMap().entrySet()) {
            ShortenedLink shortenedLink = new ShortenedLink(entry.getKey(), entry.getValue());
            links.add(shortenedLink);
        }

        return links;
    }

    private HashMap<String, String> getMap() {
        return map;
    }

    @Override
    public boolean containsValue(String key) {
        return map.containsKey(key);
    }
}
