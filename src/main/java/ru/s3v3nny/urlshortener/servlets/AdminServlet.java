package ru.s3v3nny.urlshortener.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.services.JsonConverter;
import ru.s3v3nny.urlshortener.services.LinkRepository;
import ru.s3v3nny.urlshortener.services.LinkUtils;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    JsonConverter converter = new JsonConverter();
    Error err;

    LinkUtils utils = new LinkUtils();

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String key = request.getPathInfo();
        System.out.println(key);

        if (utils.checkKey(key)) {
            key = utils.getKey(key);
        } else {
            err = new Error();
            err.setMessage("Incorrect key");
            response.getWriter().println(converter.errorToJson(err));
            return;
        }

        utils.deleteLink(response, key);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getPathInfo();

        if (!utils.checkMap()) {
            err = new Error();
            err.setMessage("HashMap is null");
            response.getWriter().print(converter.errorToJson(err));
            return;
        }

        utils.printLinks(response);
    }
}
