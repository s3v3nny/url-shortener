package ru.s3v3nny.urlshortener;

import ru.s3v3nny.urlshortener.services.JsonConverter;
import ru.s3v3nny.urlshortener.services.RedisMigration;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    private static final JsonConverter converter = new JsonConverter();
    private static final String APP_CONFIG_PATH = System.getenv("APP_CONFIG_PATH");

    public static void main(String[] args) throws Exception {

        String jettyServerConfig;
        System.out.println(APP_CONFIG_PATH);

        RedisMigration.setNullFields();

        if(APP_CONFIG_PATH == null) {
            ClassLoader classLoader = Main.class.getClassLoader();
            URL jscURL = classLoader.getResource("JettyServerConfig.json");

            File jscFile = null;

            try {
                jscFile = new File(jscURL.toURI());
            } catch (URISyntaxException e) {
                e.printStackTrace();
                System.exit(-1);
            }

            jettyServerConfig = Files.readString(Paths.get(String.valueOf(jscFile.toPath())));
        } else {
            jettyServerConfig = Files.readString(Paths.get(APP_CONFIG_PATH));
        }

        int port = converter.getJettyServerInfo(jettyServerConfig).getPort();

        ShortenerServer server = new ShortenerServer();
        server.start(port);
    }
}
