package ru.s3v3nny.urlshortener.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.s3v3nny.urlshortener.dto.ShortenedLinkDTO;
import ru.s3v3nny.urlshortener.entities.Link;

@Component
public class EntityConverter {

    @Value("${app-settings.base-url}")
    private String BASE_URL;

    public ShortenedLinkDTO entityToShortenedLinkDto(Link link) {
        String key = link.getKey();
        String shortUrl = BASE_URL.concat(key);
        ShortenedLinkDTO shortenedLinkDTO = new ShortenedLinkDTO();
        shortenedLinkDTO.setShortenedUrl(shortUrl);
        shortenedLinkDTO.setUrl(link.getUrl());
        shortenedLinkDTO.setCreatedAt(link.getCreatedAt());
        shortenedLinkDTO.setViews(link.getViews());
        return shortenedLinkDTO;
    }
}
