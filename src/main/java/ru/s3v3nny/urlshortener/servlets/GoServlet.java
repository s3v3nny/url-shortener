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

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Error err = new Error();
        String link = null;
        String key = request.getPathInfo();
        JsonConverter converter = new JsonConverter();

        if(key == null){
            err.setMessage("Incorrect key");
            response.getWriter().println(converter.errorToJson(err));
            return;
        } else {
            key = key.substring(1);
        }

        if(LinkRepository.getInstance().containsValue(key)) {
            link = LinkRepository.getInstance().getValue(key);
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", link);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            err.setMessage("Link doesn't exist in Map");
            response.getWriter().println(converter.errorToJson(err));
        }
    }
}
