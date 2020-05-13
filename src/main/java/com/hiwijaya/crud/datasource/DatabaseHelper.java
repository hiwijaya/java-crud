package com.hiwijaya.crud.datasource;

import com.hiwijaya.crud.util.Lib;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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

            Properties props = Lib.getPropertiesFile("config.properties");

            final String host = props.getProperty("HOST");
            final String user = props.getProperty("USER");
            final String password = props.getProperty("PASSWORD");

            connection = DriverManager.getConnection(host, user, password);
            System.out.println("Database connected.");

            return connection;
        }
        return connection;
    }

}
