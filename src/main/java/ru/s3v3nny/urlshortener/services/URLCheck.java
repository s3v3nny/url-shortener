package ru.s3v3nny.urlshortener.services;

import java.net.URL;

public class URLCheck {
    public static boolean isValidUrl(String link) {
        try {
            URL url = new URL(link);
            url.toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
