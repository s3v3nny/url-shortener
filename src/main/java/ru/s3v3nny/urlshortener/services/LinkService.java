package ru.s3v3nny.urlshortener.services;

import jakarta.servlet.http.HttpServletResponse;
import ru.s3v3nny.urlshortener.interfaces.LinkRepoInterface;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.servlets.AdminServlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

public class LinkService {

    LinkRepoInterface linkRepo = LinkRepoProvider.getLinkRepo();
    JsonConverter converter = new JsonConverter();
    Logger log = Logger.getLogger(AdminServlet.class.getName());

    public String createNewShortUrl(String link) throws SQLException {
        String key = UUID.randomUUID().toString().split("-")[0];
        linkRepo.addNewValue(key, link);
        return key;
    }

    public void getLink(HttpServletResponse response, String key) throws IOException, SQLException {
        if (linkRepo.containsValue(key)) {
            String link = linkRepo.getValue(key);
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", link);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            var err = new Error();
            err.setMessage("Link doesn't exist in repository");
            response.getWriter().println(converter.errorToJson(err));
        }
    }

    public void deleteLink(HttpServletResponse response, String key) throws IOException, SQLException {
        if (linkRepo.containsValue(key)) {
            linkRepo.deleteValue(key);
            response.setStatus(HttpServletResponse.SC_OK);
            log.info(key + " is deleted");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            var err = new Error();
            err.setMessage("Link doesn't exist in repository");
            response.getWriter().println(converter.errorToJson(err));
        }
    }

    public void printLinks(HttpServletResponse response) throws IOException {
        for (Map.Entry<String, String> entry : linkRepo.getMap().entrySet()) {
            response.getWriter().println(entry.getKey() + " " + entry.getValue());
        }
    }
}
