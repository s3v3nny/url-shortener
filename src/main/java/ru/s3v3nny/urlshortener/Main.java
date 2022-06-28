package ru.s3v3nny.urlshortener;

import ru.s3v3nny.urlshortener.services.JsonConverter;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    private static JsonConverter converter = new JsonConverter();

    public static void main(String args[]) throws Exception {

        ClassLoader classLoader = Main.class.getClassLoader();
        URL jscURL = classLoader.getResource("JettyServerConfig.json");

        File jscFile = null;

        try {
            jscFile = new File(jscURL.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        String jettyServerConfig = Files.readString(Paths.get(String.valueOf(jscFile.toPath())));

        int port = converter.getJettyServerInfo(jettyServerConfig).getPort();

        ShortenerServer server = new ShortenerServer();
        server.start(port);
        System.in.read();
    }
}
