package rmi;

import log.Logger;
import play.User;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class SessionManager {
    private static List<Session> sessions = new ArrayList<>();
    private static int sessionCount = 0;
    private static Logger logger = new Logger("SessionManager", Level.ALL, Level.ALL);;
    private static SessionStatus status = SessionStatus.DEFAULT;

    private SessionManager() {
    }

    static boolean get(User user) {
        boolean hasSession = false;
        if (!sessions.isEmpty()) {
            hasSession = inSession(user);
        }
        if (!hasSession) {
            sessions.add(new Session(sessionCount++, user));
            status = SessionStatus.LOGGED_IN;
            logger.log(Level.INFO, String.format("Added %s to sessions", user));
        }
        if(!sessions.isEmpty() && hasSession) {
            status = SessionStatus.ALREADY_LOGGED_IN;
        }
        return !hasSession;
    }

    static boolean release(User user) {
        boolean hasSession = false;
        if (!sessions.isEmpty()) {
            hasSession = inSession(user);
        }
        if (hasSession) {
            sessions.removeIf(s -> s.user.equals(user));
            logger.log(Level.INFO, String.format("Removed %s from sessions", user));
            status = SessionStatus.LOGGED_OUT;
        }
        return hasSession;
    }

    static boolean inSession(User user) {
        boolean hasSession = false;
        for (Session s : sessions) {
            if (s.user.equals(user)) {
                hasSession = true;
                break;
            }
        }
        return hasSession;
    }

    static SessionStatus getStatus() {
        return status;
    }

    static class Session {
        private int id;
        private User user;

        Session(int id, User user) {
            this.id = id;
            this.user = user;
        }
    }

    public enum SessionStatus {
        DEFAULT,
        LOGGED_IN,
        LOGGED_OUT,
        ALREADY_LOGGED_IN
    }
}