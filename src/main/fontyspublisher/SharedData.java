package fontyspublisher;

import log.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

public class SharedData {
    private static Logger logger;
    private static Properties prop;

    public static int getPort() {
        if (prop == null) {
            initprop();
        }
        if (prop != null) return Integer.parseInt(prop.getProperty("rmiport"));
        return 0;
    }

    public static String getRegistryName() {
        if (prop == null) {
            initprop();
        }
        if (prop != null) return prop.getProperty("registryip");
        return "";
    }

    public static String getServerName() {
        if (prop == null) {
            initprop();
        }
        if (prop != null) return prop.getProperty("servername");
        return "";
    }

    public static String getPublisherName() {
        if (prop == null) {
            initprop();
        }
        if (prop != null) return prop.getProperty("publishername");
        return "";
    }

    private static void initprop() {
        prop = new Properties();
        try {
            prop.load(new FileInputStream("db.properties"));
        } catch (IOException e) {
            if (logger == null) {
                logger = new Logger("SharedData", Level.SEVERE, Level.SEVERE);
            }
            logger.log(Level.SEVERE, e.toString());
        }
    }
}
