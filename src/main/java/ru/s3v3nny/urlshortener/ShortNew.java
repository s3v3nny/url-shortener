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
    HashMap<String, String> urlMap;
    JsonConverter CONVERTER = new JsonConverter();
    String shortID;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String contentType = request.getContentType();
        BufferedReader reader = request.getReader();

        if ("application/json".equals(contentType)) {
            String link = CONVERTER.getLink(reader.readLine()).getLink();
            if (isValidUrl(link) || link != null) {
                shortID = createNewShortUrl(link, urlMap);
            } else {
                System.out.print("Incorrect link!");
            }
        } else {
            System.out.print("Incorrect Content-Type!");
        }



        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("\"link\": \"" + shortID + "\"}");
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String createNewShortUrl(String link, HashMap<String, String> map) {
        String id = UUID.randomUUID().toString().split("-")[0];
        map.put(id, link);
        return id;
    }
}
