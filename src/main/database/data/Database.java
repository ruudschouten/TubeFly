package database.data;

import log.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

public class Database {

    private Database() { }

    private static Properties props;
    private static Connection con;

    public static Connection getCon() {
        if(con == null) {
            Logger logger = new Logger("Database", Level.SEVERE, Level.SEVERE);
            try {
                setupProperty();
                initConnection();
            } catch (IOException | SQLException e) {
                logger.logE(Level.SEVERE, e);
            }
        }
        return con;
    }

    private static void setupProperty() throws IOException {
        props = new Properties();
        props.load(new FileInputStream("db.properties"));
    }

    private static void initConnection() throws SQLException {
        String dbUrl = props.getProperty("dbUrl");
        String dbTable = props.getProperty("dbTable");
        String dbReconnect = props.getProperty("reconnect");
        String dbSsl = props.getProperty("ssl");
        String dbUser = props.getProperty("dbUser");
        String dbPass = props.getProperty("dbPass");
        con = DriverManager.getConnection(String.format("%s/%s?autoReconnect=%s&useSSL=%s", dbUrl, dbTable, dbReconnect, dbSsl), dbUser, dbPass);
    }
}
