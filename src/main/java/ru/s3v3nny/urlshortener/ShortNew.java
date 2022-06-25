package ru.s3v3nny.urlshortener;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

@WebServlet("short-new")
public class ShortNew extends HttpServlet {

    JsonConverter converter = new JsonConverter();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String contentType = request.getContentType();
        BufferedReader reader = request.getReader();
        String shortID = null;

        if ("application/json".equals(contentType)) {
            String link = converter.getLink(reader.readLine()).getLink();
            if (isValidUrl(link) || link != null) {
                shortID = createNewShortUrl(link);
                System.out.println(link + " " + shortID);
            } else {
                response.getWriter().print("Incorrect link!");
                return;
            }
        } else {
            response.getWriter().print("Incorrect Content-Type!");
        }

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print("\"link\": \"" + shortID + "\"}");
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String createNewShortUrl(String link) {
        String key = UUID.randomUUID().toString().split("-")[0];
        Singleton.getInstance().addNewValue(key, link);
        return key;
    }
}
