package ru.s3v3nny.urlshortener.services;

import jakarta.servlet.http.HttpServletResponse;
import ru.s3v3nny.urlshortener.interfaces.LinkRepoInterface;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.servlets.AdminServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class LinkService {

    LinkRepoInterface linkRepo = LinkRepoProvider.getLinkRepo();
    Logger log = Logger.getLogger(AdminServlet.class.getName());

    public String createNewShortUrl(String link) {
        String key = UUID.randomUUID().toString().split("-")[0];
        try {
            linkRepo.addNewValue(key, link);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return key;
    }

    public String getLink(String key) throws SQLException {
        if (linkRepo.containsValue(key)) {
            return linkRepo.getValue(key);
        } else {
            return null;
        }
    }

    public boolean deleteLink(String key) throws SQLException {
        if (linkRepo.containsValue(key)) {
            linkRepo.deleteValue(key);
            log.info(key + " is deleted");
            return true;
        } else {
            return false;
        }
    }

    public String shortLink(String link) {
        String shortID = createNewShortUrl(link);
        String logString = link + ": " + shortID;
        log.info(logString);
        return shortID;
    }
}
