package com.hiwijaya.crud.datasource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Happy Indra Wijaya
 */
public class DatabaseHelper {

    private static Connection connection;

    private DatabaseHelper(){
        // just to prevent instantiate
    }

    public static Connection getConnection() throws SQLException {
        if(connection == null){
            final String host = "jdbc:postgresql://localhost:5432/library";
            final String user = "postgres";
            final String password = "root";

            connection = DriverManager.getConnection(host, user, password);
            System.out.println("Database connected.");

            return connection;
        }
        return connection;
    }

}
