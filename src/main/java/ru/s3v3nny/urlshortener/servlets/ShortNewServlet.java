package ru.s3v3nny.urlshortener.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.models.Link;
import ru.s3v3nny.urlshortener.services.JsonConverter;
import ru.s3v3nny.urlshortener.services.LinkUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("short-new")
public class ShortNewServlet extends HttpServlet {

    JsonConverter converter = new JsonConverter();
    Logger log = Logger.getLogger(ShortNewServlet.class.getName());


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String contentType = request.getContentType();
        BufferedReader reader = request.getReader();
        String shortID = null;
        LinkUtils utils = new LinkUtils();
        String logString = null;
        Link linkObj = new Link();
        Error err = new Error();

        if ("application/json".equals(contentType)) {
            String link = converter.getLink(reader.readLine()).getLink();
            if (utils.isValidUrl(link) || link != null) {
                shortID = utils.createNewShortUrl(link);
                logString = link + ": " + shortID;
                log.info(logString);
            } else {
                err.setMessage("Incorrect link");
                response.getWriter().print(converter.errorToJson(err));
                return;
            }
        } else {
            err.setMessage("Incorrect Content-Type");
            response.getWriter().print(converter.errorToJson(err));
            return;
        }

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        linkObj.setLink("http://localhost:8080/go/" + shortID);
        response.getWriter().print(converter.linkToJson(linkObj));
    }
}