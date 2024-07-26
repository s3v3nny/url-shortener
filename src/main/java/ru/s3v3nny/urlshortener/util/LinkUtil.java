package ru.s3v3nny.urlshortener.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LinkUtil{

    public String generateKey() {
        return UUID.randomUUID().toString().split("-")[0];
    }

}
