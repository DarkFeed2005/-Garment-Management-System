package com.nisal.garment.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DB {

    private static final Properties P = new Properties();

    static {
        try (InputStream input =
                     DB.class.getClassLoader().getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "db.properties not found in src/main/resources"
                );
            }

            P.load(input);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to load db.properties", e
            );
        }
    }

    private DB() {
    }

    public static Connection get() throws SQLException {

        String url = P.getProperty("db.url");
        String user = P.getProperty("db.user");
        String password = P.getProperty("db.password");

        if (url == null || user == null || password == null) {
            throw new SQLException(
                    "Database configuration is incomplete. " +
                    "Check db.properties."
            );
        }

        return DriverManager.getConnection(
                url,
                user,
                password
        );
    }

    public static boolean test() {

        try (Connection connection = get()) {

            System.out.println(
                    "MySQL connected successfully."
            );

            return true;

        } catch (SQLException e) {

            System.err.println(
                    "MySQL connection failed."
            );

            System.err.println(
                    "URL: " + P.getProperty("db.url")
            );

            System.err.println(
                    "User: " + P.getProperty("db.user")
            );

            System.err.println(
                    "Error: " + e.getMessage()
            );

            return false;
        }
    }
}