package ru.s3v3nny.urlshortener.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.s3v3nny.urlshortener.interfaces.LinkRepoInterface;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.services.JsonConverter;
import ru.s3v3nny.urlshortener.services.LinkRepoProvider;
import ru.s3v3nny.urlshortener.services.LinkService;
import ru.s3v3nny.urlshortener.utils.LinkUtils;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    JsonConverter converter = new JsonConverter();

    LinkUtils utils = new LinkUtils();
    LinkService service = new LinkService();

    LinkRepoInterface linkRepo = LinkRepoProvider.getLinkRepo();

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
            if (service.deleteLink(key)) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                var err = new Error();
                err.setMessage("Link doesn't exist in repository");
                response.getWriter().println(converter.errorToJson(err));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getPathInfo();

        String result = null;

        try {
            result = linkRepo.getValues();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (result == null) {
            var err = new Error();
            err.setMessage("Repository is null");
            response.getWriter().print(converter.errorToJson(err));
        } else {
            response.getWriter().print(result);
        }
    }
}
