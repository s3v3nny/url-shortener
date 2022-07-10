package ru.s3v3nny.urlshortener.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.services.JsonConverter;
import ru.s3v3nny.urlshortener.services.LinkRepository;

import java.io.IOException;

@WebServlet("go")
public class GoServlet extends HttpServlet {

    Error err;
    JsonConverter converter = new JsonConverter();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String key = request.getPathInfo();

        if (key == null || key.isEmpty()) {
            err = new Error();
            err.setMessage("Incorrect key");
            response.getWriter().println(converter.errorToJson(err));
            return;
        } else {
            key = key.substring(1);
        }

        String link = null;

        if (LinkRepository.getInstance().containsValue(key)) {
            link = LinkRepository.getInstance().getValue(key);
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", link);
        } else {
            err = new Error();
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            err.setMessage("Link doesn't exist in Map");
            response.getWriter().println(converter.errorToJson(err));
        }
    }
}
