import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;
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
    FileHandler fh;

    private Log() {
        averageResponseTime = 0;
    }

    public static Log getInstance(){
        if (instance == null){
            instance = new Log();
        }
        return instance;
    }

    public void logGetRequest(String url, String statusCode, int requestTime){
        Logger logger = Logger.getLogger("MyLog");
        setupLogger(logger);
        logger.info("status:"+statusCode+"; requestTime:"+requestTime+"; URL:"+url+";");
    }

    private void setupLogger(Logger logger) {
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
}
