package ru.s3v3nny.urlshortener.services;

import ru.s3v3nny.urlshortener.interfaces.LinkRepoInterface;
import ru.s3v3nny.urlshortener.repositories.MapRepo;

import java.util.Objects;

public class LinkRepoProvider {

    private static LinkRepoInterface linkRepo;

    public static LinkRepoInterface getLinkRepo() {
        if (!Objects.equals(linkRepo, new MapRepo())) {
            linkRepo = new MapRepo();
        }
        return linkRepo;
    }
}
