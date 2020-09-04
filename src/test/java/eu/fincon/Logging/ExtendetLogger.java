package eu.fincon.Logging;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.logging.*;

public class ExtendetLogger {
    static Logger logger = null;
    static String strLogFilePath = "Log.html";
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
        logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());
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
            // Setzt den Formatter auf die CustomFormatter Klasse
            fh.setFormatter(new CustomFormatter());
            // Fügt den Handler (FileHandler) dem Logger hinzu
            logger.addHandler(fh);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void LogEntry(LogTypes pltType, String pstrMessage)
    {
        String strClassName = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass().toString();
        String strMethodName = Thread.currentThread().getStackTrace()[2].getMethodName();

        pstrMessage = pstrMessage + ";" + strClassName + ";" + strMethodName;
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
