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
import java.util.Map;
import java.util.logging.Logger;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    JsonConverter converter = new JsonConverter();
    Error err;
    Logger log = Logger.getLogger(AdminServlet.class.getName());

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String key = request.getPathInfo();

        if (key == null) {
            err = new Error();
            err.setMessage("Incorrect key");
            response.getWriter().println(converter.errorToJson(err));
            return;
        } else {
            key = key.substring(1);
        }


        if (LinkRepository.getInstance().containsValue(key)) {
            LinkRepository.getInstance().deleteValue(key);
            response.setStatus(HttpServletResponse.SC_OK);
            log.info(key + " is deleted");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            err = new Error();
            err.setMessage("Link doesn't exist in Map");
            response.getWriter().println(converter.errorToJson(err));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getPathInfo();

        if (!"/all".equals(key)) return;

        if (LinkRepository.getInstance().getMap() == null) {
            err = new Error();
            err.setMessage("HashMap is null");
            response.getWriter().print(converter.errorToJson(err));
            return;
        }

        for (Map.Entry<String, String> entry : LinkRepository.getInstance().getMap().entrySet()) {
            response.getWriter().println(entry.getKey() + " " + entry.getValue());
        }
    }
}
