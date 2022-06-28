package ru.s3v3nny.urlshortener.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.s3v3nny.urlshortener.models.Error;
import ru.s3v3nny.urlshortener.services.JsonConverter;
import ru.s3v3nny.urlshortener.services.MapUtils;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    JsonConverter converter = new JsonConverter();
    Error err = new Error();

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getPathInfo();

        Logger log = Logger.getLogger(AdminServlet.class.getName());
        if(key == null) {
            err.setMessage("Incorrect key");
            response.getWriter().println(converter.errorToJson(err));
            return;
        } else {
            key = key.substring(1);
        }


        if(MapUtils.getInstance().containsValue(key)){
            MapUtils.getInstance().deleteValue(key);
            response.setStatus(HttpServletResponse.SC_OK);
            log.info(key + " is deleted");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            err.setMessage("Link doesn't exist in Map");
            response.getWriter().println(converter.errorToJson(err));
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getPathInfo();

        if(!"/all".equals(key)) return;

        if(MapUtils.getInstance().getMap() == null) {
            err.setMessage("HashMap is null");
            response.getWriter().print(converter.errorToJson(err));
            return;
        }

        for(Map.Entry<String, String> entry : MapUtils.getInstance().getMap().entrySet()) {
            response.getWriter().println(entry.getKey() + " " + entry.getValue());
        }
    }
}
