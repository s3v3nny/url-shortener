package ru.s3v3nny.urlshortener.util;

import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Component
public class URLUtil {
    public boolean validateURL(String url) {
        try {
            URL temp = new URL(url);
            temp.toURI();
            return true;
        } catch (URISyntaxException | MalformedURLException e) {
            return false;
        }
    }
}
