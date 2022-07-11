package ru.s3v3nny.urlshortener.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.services.JsonConverter;
import ru.s3v3nny.urlshortener.services.LinkUtils;

import java.io.IOException;

@WebServlet("go")
public class GoServlet extends HttpServlet {

    Error err;
    JsonConverter converter = new JsonConverter();
    LinkUtils utils = new LinkUtils();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String key = request.getPathInfo();

        if (utils.checkKey(key)) {
            key = utils.getKey(key);
        } else {
            err = new Error();
            err.setMessage("Incorrect key");
            response.getWriter().println(converter.errorToJson(err));
            return;
        }

        utils.getLink(response, key);
    }
}
