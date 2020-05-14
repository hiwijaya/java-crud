package com.hiwijaya.crud.datasource;

import com.hiwijaya.crud.util.Lib;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author Happy Indra Wijaya
 */
public class DatabaseHelper {

    private static final boolean USE_CONNECTION_POOL = false;
    private static HikariDataSource poolSource;
    private static Connection connection;


    private DatabaseHelper(){ }


    public static Connection getConnection() throws SQLException {
        if(USE_CONNECTION_POOL){
            initConnectionPool();
            return poolSource.getConnection();
        }
        else{
            initSingletonConnection();
            return connection;
        }
    }

    private static void initConnectionPool(){
        if(poolSource == null){
            Properties props = Lib.getPropertiesFile("hikari.properties");
            HikariConfig config = new HikariConfig(props);
            poolSource = new HikariDataSource(config);
        }
    }

    private static void initSingletonConnection() throws SQLException {
        if(connection == null){
            Properties props = Lib.getPropertiesFile("config.properties");

            final String host = props.getProperty("HOST");
            final String user = props.getProperty("USER");
            final String password = props.getProperty("PASSWORD");

            // init singleton object connection
            connection = DriverManager.getConnection(host, user, password);
            System.out.println("Database connected.");

        }
    }

}
