package main.database.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private Properties props;
    private Connection con;

    private void setupProperty() throws IOException {
        props = new Properties();
        props.load(new FileInputStream("db.properties"));
    }

    private void initConnection() throws SQLException {
        String dbUrl = props.getProperty("dbUrl");
        String dbTable = props.getProperty("dbTable");
        String dbReconnect = props.getProperty("reconnect");
        String dbSsl = props.getProperty("ssl");
        String dbUser = props.getProperty("dbUser");
        String dbPass = props.getProperty("dbPass");
        con = DriverManager.getConnection(String.format("%s/%s?autoReconnect=%s&useSSL=%s", dbUrl, dbTable, dbReconnect, dbSsl), dbUser, dbPass);
    }
}
