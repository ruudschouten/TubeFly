package log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;

public class Logger {
    private java.util.logging.Logger javaLogger;

    public Logger(String name, Level fileLogging, Level commandLogging) {
        setupLogger(name, fileLogging, commandLogging);
    }

    private void setupLogger(String name, Level fileLogging, Level commandLogging) {
        try {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());
            FileHandler fh = new FileHandler(String.format("logs/%s-%s.log", name, timeStamp));
            fh.setLevel(fileLogging);
            ConsoleHandler ch = new ConsoleHandler();
            ch.setLevel(commandLogging);
            javaLogger = java.util.logging.Logger.getLogger(name);
            javaLogger.addHandler(fh);
            javaLogger.setLevel(Level.ALL);
        } catch (IOException e) {
            javaLogger.log(Level.SEVERE, e.getMessage());
        }
    }

    public void log(Level level, String message){
        javaLogger.log(level, message);
    }
}
