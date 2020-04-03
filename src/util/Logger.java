package util;


import com.intellij.notification.*;

/**
 * create on 17/12/16
 * @author wujs
 */
public class Logger {
    private static String NAME;
    private static int LEVEL = 0;

    public static final int DEBUG = 3;
    public static final int INFO = 2;
    public static final int WARN = 1;
    public static final int ERROR = 0;

    public static void init(String name,int level) {
        NAME = name;
        LEVEL = level;
        NotificationsConfiguration.getNotificationsConfiguration().register(NAME, NotificationDisplayType.NONE);
    }

    public static void debug(String text) {
        if (LEVEL >= DEBUG) {
            Notifications.Bus.notify(
                    new Notification(NAME, NAME + " [DEBUG]", text, NotificationType.INFORMATION));
        }
    }

    public static void info(String text) {
        if (LEVEL > INFO) {
            Notifications.Bus.notify(
                    new Notification(NAME, NAME + " [INFO]", text, NotificationType.INFORMATION));
        }
    }

    public static void warn(String text) {
        if (LEVEL > WARN) {
            Notifications.Bus.notify(
                    new Notification(NAME, NAME + " [WARN]", text, NotificationType.WARNING));
        }
    }

    public static void error(String text) {
        if (LEVEL > ERROR) {
            Notifications.Bus.notify(
                    new Notification(NAME, NAME + " [ERROR]", text, NotificationType.ERROR));
        }
    }
}
