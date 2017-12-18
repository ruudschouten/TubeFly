package main.database.data;

import main.log.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

public class Database {
    private Logger logger;
    private Properties props;
    private Connection con;

    public Database() {
        logger = new Logger("Database", Level.SEVERE, Level.SEVERE);
        try {
            setupProperty();
            initConnection();
        } catch (IOException | SQLException e) {
            logger.log(Level.SEVERE, e.toString());
        }
    }

    public Connection getCon() {
        return con;
    }

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
