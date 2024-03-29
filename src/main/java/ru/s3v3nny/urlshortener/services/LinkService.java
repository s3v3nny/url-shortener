package ru.s3v3nny.urlshortener.services;


import ru.s3v3nny.urlshortener.interfaces.LinkRepoInterface;
import ru.s3v3nny.urlshortener.interfaces.RedisRepoInterface;
import ru.s3v3nny.urlshortener.models.LinkModel;
import ru.s3v3nny.urlshortener.models.ShortenedLink;
import ru.s3v3nny.urlshortener.servlets.AdminServlet;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class LinkService {

    LinkRepoInterface linkRepo = LinkRepoProvider.getLinkRepo();
    Logger log = Logger.getLogger(AdminServlet.class.getName());
    RedisRepoInterface redisRepo = RedisRepoProvider.getRedisRepo();
    LinkDAO linkDAO = new LinkDAO();

    public String createNewShortUrl(String link) {
        String key = UUID.randomUUID().toString().split("-")[0];
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LinkModel linkModel = new LinkModel(key, link, timestamp);
        linkDAO.update(linkModel);
            //linkRepo.addNewValue(key, link);
        redisRepo.addValue(key);

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
        ArrayList<ShortenedLink> links = linkRepo.getValues();

        if(links == null) {
            return null;
        }

        String result = "";
        for(ShortenedLink s : links) {
            result += "ID " + s.getKey() + " equals link " + s.getLink() + ". Redirects: " + redisRepo.getViews(s.getKey()) + "\n";
        }

        return result;
    }
}
