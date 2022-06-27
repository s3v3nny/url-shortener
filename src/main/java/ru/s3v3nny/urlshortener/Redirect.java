package ru.s3v3nny.urlshortener;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("go")
public class Redirect extends HttpServlet {
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String link = null;
        String key = request.getPathInfo();

        if(key == null){
            response.getWriter().println("No key?");
            return;
        } else {
            key = key.substring(1);
        }

        if(Singleton.getInstance().containsValue(key)) {
            link = Singleton.getInstance().getValue(key);
            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", link);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().println("No link?");
        }
    }
}
