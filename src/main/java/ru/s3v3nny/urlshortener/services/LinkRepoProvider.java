package ru.s3v3nny.urlshortener.services;

import ru.s3v3nny.urlshortener.repositories.MapRepo;

import java.util.Objects;

public class LinkRepoProvider {

    private static LinkRepoInterface repoInterface;

    public static LinkRepoInterface getLinkRepo() {
        if (!Objects.equals(repoInterface, new MapRepo())) {
            repoInterface = new MapRepo();
        }
        return repoInterface;
    }
}
