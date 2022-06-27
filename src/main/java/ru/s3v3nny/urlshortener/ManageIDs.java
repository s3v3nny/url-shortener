package ru.s3v3nny.urlshortener;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/admin/xxxxxx")
public class ManageIDs extends HttpServlet {
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getPathInfo();

        if(key == null || "/all".equals(key)) {
            response.getWriter().println("No key?");
            return;
        } else {
            key = key.substring(1);
        }


        if(Singleton.getInstance().containsValue(key)){
            Singleton.getInstance().deleteValue(key);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("Link deleted!");
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("No link?");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getPathInfo();

        if(!"/all".equals(key)) return;

        for(Map.Entry<String, String> entry : Singleton.getInstance().getMap().entrySet()) {
            response.getWriter().println(entry.getKey() + " " + entry.getValue());
        }
    }
}
