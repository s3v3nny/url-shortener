package ru.s3v3nny.urlshortener.utils;

import ru.s3v3nny.urlshortener.interfaces.LinkRepoInterface;
import ru.s3v3nny.urlshortener.services.LinkRepoProvider;
import ru.s3v3nny.urlshortener.services.URLCheck;

public class LinkUtils {
    LinkRepoInterface linkRepo = LinkRepoProvider.getLinkRepo();

    public boolean checkKey(String key) {
        return key != null && !key.isEmpty();
    }

    public String formatKey(String key) {
        return key.substring(1);
    }

    public boolean checkMap() {
        return linkRepo.getMap() != null;
    }

    public boolean checkContentType(String contentType) {
        return "application/json".equals(contentType);
    }

    public boolean checkURL(String url) {
        return URLCheck.isValidUrl(url) && url != null;
    }
}
