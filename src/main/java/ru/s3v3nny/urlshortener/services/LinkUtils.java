package ru.s3v3nny.urlshortener.services;

import jakarta.servlet.http.HttpServletResponse;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.servlets.AdminServlet;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class LinkUtils {

    Error err;
    JsonConverter converter = new JsonConverter();
    Logger log = Logger.getLogger(AdminServlet.class.getName());

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
        LinkRepository.getInstance().addNewValue(key, link);
        return key;
    }

    public boolean checkKey(String key) {
        return key != null && !key.isEmpty();
    }

    public String getKey(String key) {
        return key.substring(1);
    }

    public void getLink(HttpServletResponse response, String key) throws IOException {
        System.out.println(key);
        if (LinkRepository.getInstance().containsValue(key)) {
            String link = LinkRepository.getInstance().getValue(key);
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", link);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            err = new Error();
            err.setMessage("Link doesn't exist in Map");
            response.getWriter().println(converter.errorToJson(err));
        }
    }

    public void deleteLink(HttpServletResponse response, String key) throws IOException {
        if (LinkRepository.getInstance().containsValue(key)) {
            LinkRepository.getInstance().deleteValue(key);
            response.setStatus(HttpServletResponse.SC_OK);
            log.info(key + " is deleted");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            err = new Error();
            err.setMessage("Link doesn't exist in Map");
            response.getWriter().println(converter.errorToJson(err));
        }
    }

    public void printLinks(HttpServletResponse response) throws IOException {
        for (Map.Entry<String, String> entry : LinkRepository.getInstance().getMap().entrySet()) {
            response.getWriter().println(entry.getKey() + " " + entry.getValue());
        }
    }

    public boolean checkMap() {
        return LinkRepository.getInstance().getMap() != null;
    }

    public boolean checkContentType(String contentType) {
        return "application/json".equals(contentType);
    }

    public boolean checkURL(String url) {
        return isValidUrl(url) && url != null;
    }
}
