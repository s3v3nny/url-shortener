package ru.s3v3nny.urlshortener.interfaces;

import ru.s3v3nny.urlshortener.models.ShortenedLink;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LinkRepoInterface {
    void addNewValue(String key, String value) throws SQLException;

    String getValue(String key) throws SQLException;

    void deleteValue(String key) throws SQLException;

    ArrayList<ShortenedLink> getValues() throws SQLException;

    boolean containsValue(String key) throws SQLException;

}
