package ru.s3v3nny.urlshortener.services;

import ru.s3v3nny.urlshortener.configs.JettyServerInfo;
import ru.s3v3nny.urlshortener.models.Link;

import com.google.gson.Gson;

public class JsonConverter {
    private static final Gson gson = new Gson();

    public Link getLink(String json) {
        return gson.fromJson(json, Link.class);
    }

    public JettyServerInfo getJettyServerInfo(String json) {
        return gson.fromJson(json, JettyServerInfo.class);
    }
}
