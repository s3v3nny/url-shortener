package ru.s3v3nny.urlshortener;

public class Main {
    public static void main(String args[]) throws Exception {
        ShortenerServer server = new ShortenerServer();
        server.start();
        System.in.read();
    }
}
