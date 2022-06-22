package ru.s3v3nny.urlshortener;

import ru.s3v3nny.urlshortener.models.Link;

import com.google.gson.Gson;

public class JsonConverter {
    private static final Gson GSON = new Gson();

    public Link getLink(String json) {
        return GSON.fromJson(json, Link.class);
    }
}
