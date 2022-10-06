package ru.s3v3nny.urlshortener.utils;

import ru.s3v3nny.urlshortener.interfaces.LinkRepoInterface;
import ru.s3v3nny.urlshortener.services.LinkRepoProvider;
import ru.s3v3nny.urlshortener.services.URLCheck;

public class LinkUtils {
    public boolean checkKey(String key) {
        return key != null && !key.isEmpty();
    }

    public boolean checkURL(String url) {
        return URLCheck.isValidUrl(url) && url != null;
    }
}
