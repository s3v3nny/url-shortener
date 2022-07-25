package ru.s3v3nny.urlshortener.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.services.JsonConverter;
import ru.s3v3nny.urlshortener.services.LinkService;
import ru.s3v3nny.urlshortener.utils.LinkUtils;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("go")
public class GoServlet extends HttpServlet {

    JsonConverter converter = new JsonConverter();

    LinkUtils utils = new LinkUtils();
    LinkService service = new LinkService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String key = request.getPathInfo();

        if (utils.checkKey(key)) {
            key = utils.formatKey(key);
        } else {
            var err = new Error();
            err.setMessage("Incorrect key");
            response.getWriter().println(converter.errorToJson(err));
            return;
        }

        try {
            service.getLink(response, key);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
