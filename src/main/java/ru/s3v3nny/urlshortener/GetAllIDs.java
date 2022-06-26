package ru.s3v3nny.urlshortener;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

@WebServlet("/admin/all")
public class GetAllIDs extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        for(Map.Entry<String, String> entry : Singleton.getInstance().getMap().entrySet()) {
            response.getWriter().println(entry.getKey() + " " + entry.getValue());
        }
    }
}
