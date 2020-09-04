package eu.fincon.Logging;
import java.io.File;
import java.io.IOException;
import java.util.logging.*;

public class ExtendetLogger {
    static Logger logger = null;
    static String strLogFilePath = "Log.log";
    public enum LogTypes{
        severe,
        warning,
        info,
        config,
        fine,
        finer,
        finest
    }
    public static void setup()
    {
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        createFileHandler();
        setLogLevel(Level.ALL);
    }

    private static void createFileHandler() {
        FileHandler fh;
        File tmpLogFile = new File(strLogFilePath);
        if (tmpLogFile.exists())
        {
            try {
                tmpLogFile.delete();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        try {
            // This block configure the logger with handler and formatter
            fh = new FileHandler(strLogFilePath);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void LogEntry(LogTypes pltType, String pstrMessage)
    {
        switch (pltType)
        {
            case severe:
                logger.severe(pstrMessage);
                break;
            case warning:
                logger.warning(pstrMessage);
                break;
            case info:
                logger.info(pstrMessage);
                break;
            case config:
                logger.config(pstrMessage);
                break;
            case fine:
                logger.fine(pstrMessage);
                break;
            case finer:
                logger.finer(pstrMessage);
                break;
            case finest:
                logger.finest(pstrMessage);
                break;
        }
    }
    public static void setLogLevel(Level pLogLevel)
    {
        logger.setLevel(pLogLevel);
    }
}
