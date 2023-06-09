package ru.s3v3nny.urlshortener.services;


import ru.s3v3nny.urlshortener.interfaces.LinkRepoInterface;
import ru.s3v3nny.urlshortener.interfaces.RedisRepoInterface;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.models.Link;
import ru.s3v3nny.urlshortener.models.Result;
import ru.s3v3nny.urlshortener.models.ShortenedLink;
import ru.s3v3nny.urlshortener.servlets.Admin;
import ru.s3v3nny.urlshortener.utils.LinkUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Logger;

public class LinkService {

    private final JsonConverter converter = new JsonConverter();
    private final LinkUtils utils = new LinkUtils();
    private final LinkRepoInterface linkRepo = LinkRepoProvider.getLinkRepo();
    private final Logger log = Logger.getLogger(Admin.class.getName());
    private final RedisRepoInterface redisRepo = RedisRepoProvider.getRedisRepo();

    public Result<Link, Error> createNewShortUrl(String input) {

        String link = converter.getLink(input).getLink();
        if (!utils.checkURL(link)) {
            var error = new Error();
            error.setMessage("Incorrect link!");

            return new Result<>(null, error);
        }

        String key = UUID.randomUUID().toString().split("-")[0];
        try {
            linkRepo.addNewValue(key, link);
            redisRepo.addValue(key);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String logString = link + ": " + key;
        log.info(logString);

        Link linkObj = new Link();
        linkObj.setLink("http://localhost:8080/go/" + key);
        return new Result<>(linkObj, null);
    }

    public Result<Link, Error> getLink(String key) throws SQLException {
        if (!utils.checkKey(key)) {
            var err = new Error();
            err.setMessage("Incorrect key!");
            return new Result<>(null, err);
        }

        if (linkRepo.containsValue(key) && redisRepo.containsValue(key)) {
            redisRepo.incrementValue(key);
            Link link = new Link();
            link.setLink(linkRepo.getValue(key));
            return new Result<>(link, null);
        } else {
            var err = new Error();
            err.setMessage("Link doesn't exist in repository!");
            return new Result<>(null, err);
        }
    }

    public Result<Link, Error> deleteLink(String key) throws SQLException {
        if (!utils.checkKey(key)) {
            var err = new Error();
            err.setMessage("Incorrect key");
            return new Result<>(null, err);
        }

        if (linkRepo.containsValue(key) && redisRepo.containsValue(key)) {
            linkRepo.deleteValue(key);
            redisRepo.deleteValue(key);
            log.info(key + " is deleted");
            return new Result<>(null, null);
        } else {
            var err = new Error();
            err.setMessage("Link doesn't exist in repository!");
            return new Result<>(null, err);
        }
    }

    public Result<String, Error> getLinks() throws SQLException {

        ArrayList<ShortenedLink> links = linkRepo.getValues();

        if (links == null) {
            var err = new Error();
            err.setMessage("Repository is null!");
            return new Result<>(null, err);
        }

        String result = "";
        for (ShortenedLink s : links) {
            result += "ID " + s.getKey() + " equals link " + s.getLink() + ". Redirects: " + redisRepo.getViews(s.getKey()) + "\n";
        }

        return new Result<>(result, null);
    }
}
