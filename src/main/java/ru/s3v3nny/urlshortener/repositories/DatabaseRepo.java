package ru.s3v3nny.urlshortener.repositories;

import ru.s3v3nny.urlshortener.interfaces.LinkRepoInterface;
import ru.s3v3nny.urlshortener.services.LinkRepoProvider;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DatabaseRepo implements LinkRepoInterface {

    private Connection getNewConnection() throws SQLException {
        String url = "jdbc:mariadb://sloth-1.suslovd.ru:9106/keys_db";
        String user = "javauser";
        String password = "VerySecureJavaPassword";
        return DriverManager.getConnection(url, user, password);
    }

    @Override
    public void addNewValue(String key, String value) throws SQLException {
        long currentTime = System.currentTimeMillis() / 1000L;
        Timestamp timestamp = new Timestamp(currentTime);

        Connection connection = getNewConnection();
        String update = "INSERT INTO links VALUES (?, ?, ?);";
        PreparedStatement statement = connection.prepareStatement(update);
        statement.setString(1, key);
        statement.setString(2, value);
        statement.setTimestamp(3, timestamp);

        statement.executeUpdate();
    }

    @Override
    public String getValue(String key) throws SQLException {
        Connection connection = getNewConnection();
        String query = "SELECT * FROM links WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, key);

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return resultSet.getString("link");
    }

    @Override
    public void deleteValue(String key) throws SQLException {
        Connection connection = getNewConnection();
        String update = "DELETE FROM links WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(update);
        statement.setString(1, key);

        statement.executeUpdate();
    }

    @Override
    public HashMap<String, String> getMap() {
        return null;
    }

    @Override
    public boolean containsValue(String key) throws SQLException {
        Connection connection = getNewConnection();
        String query = "SELECT * FROM links WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, key);

        ResultSet resultSet = statement.executeQuery();

        return resultSet.isLast();
    }


}
