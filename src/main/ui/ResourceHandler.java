package ui;

import log.Logger;
import rmi.ClientContainer;

public class ResourceHandler {
    private ResourceHandler() {
    }

    private static ClientContainer container = null;
    private static Logger logger = null;

    public static final String PAUSE_CHAR = "\u23F8";
    public static final String PLAY_CHAR = "▶";

    public static void setContainer(ClientContainer container) {
        ResourceHandler.container = container;
    }

    public static ClientContainer getContainer() {
        return container;
    }

    public static void setLogger(Logger logger) {
        ResourceHandler.logger = logger;
    }

    public static Logger getLogger() {
        return logger;
    }
}
