package ru.s3v3nny.urlshortener.services;

import ru.s3v3nny.urlshortener.interfaces.LinkRepoInterface;
import ru.s3v3nny.urlshortener.repositories.DatabaseRepo;
import ru.s3v3nny.urlshortener.repositories.MapRepo;


public class LinkRepoProvider {

    private static LinkRepoInterface linkRepo;
    private static final String DATA_SOURCE = System.getenv("DATA_SOURCE");

    public static LinkRepoInterface getLinkRepo() {
        if(linkRepo == null) {
            if("mysql".equals(DATA_SOURCE)) {
                linkRepo = new DatabaseRepo();
            } else {
                linkRepo = new MapRepo();
            }
        }

        return linkRepo;
    }
}
