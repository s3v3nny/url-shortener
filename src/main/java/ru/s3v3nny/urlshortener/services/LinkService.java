package ru.s3v3nny.urlshortener.services;


import ru.s3v3nny.urlshortener.interfaces.LinkRepoInterface;
import ru.s3v3nny.urlshortener.interfaces.RedisRepoInterface;
import ru.s3v3nny.urlshortener.servlets.AdminServlet;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class LinkService {

    LinkRepoInterface linkRepo = LinkRepoProvider.getLinkRepo();
    Logger log = Logger.getLogger(AdminServlet.class.getName());
    RedisRepoInterface redisRepo = RedisRepoProvider.getRedisRepo();

    public String createNewShortUrl(String link) {
        String key = UUID.randomUUID().toString().split("-")[0];
        try {
            linkRepo.addNewValue(key, link);
            redisRepo.addValue(key);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return key;
    }

    public String getLink(String key) throws SQLException {
        if (linkRepo.containsValue(key) && redisRepo.containsValue(key)) {
            redisRepo.incrementValue(key);
            return linkRepo.getValue(key);
        } else {
            return null;
        }
    }

    public boolean deleteLink(String key) throws SQLException {
        if (linkRepo.containsValue(key) && redisRepo.containsValue(key)) {
            linkRepo.deleteValue(key);
            redisRepo.deleteValue(key);
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

    public String getLinks() throws SQLException {
        ArrayList<String> links = linkRepo.getValues();

        if(links == null) {
            return null;
        }

        String result = "";
        for(String s : links) {
            System.out.print(s);
            String key = s.substring(0, 8);
            result += s + " " + redisRepo.getViews(key) + "\n";
        }

        return result;
    }
}
