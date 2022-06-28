package ru.s3v3nny.urlshortener.services;

import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.models.JettyServerInfo;
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

    public String errorToJson(Error err) {
        return gson.toJson(err);
    }

    public String linkToJson(Link link) {
        return gson.toJson(link);
    }
}
