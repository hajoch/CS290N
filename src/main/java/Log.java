import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Kyrre on 11.03.2015.
 */
public class Log {

    private static Log instance = null;
    private double averageResponseTime;
    private int loggedResponseCount;
    private PrintWriter writer;
    private Logger logger;
    private Logger errorLogger;
    FileHandler fh;

    private Log() {
        averageResponseTime = 0;
        logger = Logger.getLogger("mainLog");
        errorLogger = Logger.getLogger("errorLog");
    }

    public static Log getInstance(){
        if (instance == null){
            instance = new Log();
        }
        return instance;
    }

    public void logGetRequest(String url, int statusCode, int requestTime, int responseLength){
        setupMainLogger();
        logger.info("status:"+statusCode+"; requestTime:"+requestTime+" responseLength:"+responseLength+"; URL:"+url+";");
    }

    public void logGetRequestNot200(String url, int statusCode) {
        setupErrorLogger();
        errorLogger.info("status:"+statusCode+"; URL:"+url+";");
    }
    private void setupMainLogger() {
        try {
            if (fh == null){
                fh = new FileHandler("log_"+new SimpleDateFormat("yyyy-MM-dd.HH.mm.ss").format(Calendar.getInstance().getTime())+".log");
                logger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupErrorLogger() {
        try {
            if (fh == null){
                fh = new FileHandler("ErrorLog_"+new SimpleDateFormat("yyyy-MM-dd.HH.mm.ss").format(Calendar.getInstance().getTime())+".log");
                errorLogger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);
            }

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
