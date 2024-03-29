package ru.s3v3nny.urlshortener.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.models.Link;
import ru.s3v3nny.urlshortener.services.JsonConverter;
import ru.s3v3nny.urlshortener.services.LinkService;
import ru.s3v3nny.urlshortener.utils.LinkUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet("short-new")
public class ShortNewServlet extends HttpServlet {

    JsonConverter converter = new JsonConverter();

    LinkUtils utils = new LinkUtils();
    LinkService service = new LinkService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String contentType = request.getContentType();
        BufferedReader reader = request.getReader();

        String shortID;

        if (utils.checkContentType(contentType)) {
            String link = converter.getLink(reader.readLine()).getLink();
            if (utils.checkURL(link)) {
                shortID = service.shortLink(link);
            } else {
                var err = new Error();
                err.setMessage("Incorrect link");
                response.getWriter().print(converter.errorToJson(err));
                return;
            }
        } else {
            var err = new Error();
            err.setMessage("Incorrect Content-Type");
            response.getWriter().print(converter.errorToJson(err));
            return;
        }

        Link linkObj = new Link();

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        linkObj.setLink("http://localhost:8080/go/" + shortID);
        response.getWriter().print(converter.linkToJson(linkObj));
    }
}
