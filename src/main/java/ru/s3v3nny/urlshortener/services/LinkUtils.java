package ru.s3v3nny.urlshortener.services;

import java.net.URL;
import java.util.UUID;

public class LinkUtils {

    public boolean isValidUrl(String link) {
        try {
            URL url = new URL(link);
            url.toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String createNewShortUrl(String link) {
        String key = UUID.randomUUID().toString().split("-")[0];
        MapUtils.getInstance().addNewValue(key, link);
        return key;
    }
}
