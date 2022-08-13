package ru.s3v3nny.urlshortener.interfaces;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LinkRepoInterface {
    void addNewValue(String key, String value) throws SQLException;

    String getValue(String key) throws SQLException;

    void deleteValue(String key) throws SQLException;

    ArrayList<String> getValues() throws SQLException;

    boolean containsValue(String key) throws SQLException;

}
