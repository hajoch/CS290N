import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Kyrre on 11.03.2015.
 */
public class Log {

    private static Log instance = null;
    private BigInteger totalTimeSpent;
    private BigInteger totalTimeSpentOnNewTerm;
    private int loggedResponseCount = 0;
    private int logged200ResponseCount = 0;
    private int logged200ResponseCountNewTerm = 0;
    private PrintWriter writer;
    private Logger logger;
    private Logger errorLogger;
    private Logger overviewLogger;
    private FileHandler fh;
    private FileHandler fhError;
    private FileHandler fhOverview;
    private HashSet<String> completedQueries;

    private Log() {
        logger = Logger.getLogger("mainLog");
        errorLogger = Logger.getLogger("errorLog");
        overviewLogger = Logger.getLogger("overviewLog");
        completedQueries = new HashSet<String>();
        totalTimeSpent = new BigInteger("0");
        totalTimeSpentOnNewTerm = new BigInteger("0");
    }

    public static Log getInstance(){
        if (instance == null){
            instance = new Log();
        }
        return instance;
    }

    public synchronized void logGetRequest(String url, int statusCode, int requestTime, int responseLength) {
        setupMainLogger();
        setupOverviewLogger();
        loggedResponseCount++;
        logged200ResponseCount++;
        totalTimeSpent = totalTimeSpent.add(new BigInteger(String.valueOf(requestTime)));

        if (!completedQueries.contains(url)) {
            logger.info("status:" + statusCode + "; requestTime:" + requestTime + "; firstTimeSearch:true" + "; URL:" + url + " responseLength:" + responseLength + ";");
            logged200ResponseCountNewTerm++;
            totalTimeSpentOnNewTerm = totalTimeSpentOnNewTerm.add(new BigInteger(String.valueOf(requestTime)));
            completedQueries.add(url);
        } else {
            logger.info("status:" + statusCode + "; requestTime:" + requestTime + "; firstTimeSearch:false" + "; URL:" + url + " responseLength:" + responseLength + ";");
        }
        if (logged200ResponseCount%100 == 0 || logged200ResponseCount == 0){
            overviewLogger.info("Response count:" + loggedResponseCount + "; Response count (200ok):" + logged200ResponseCount+
                    "; Average response time(200ok):" + getAverageResponseTime()+ "; Average response time(200ok) new terms:" + getAverageResponseTimeNewTerms());
        }

    }

    public synchronized void logGetRequestNot200(String url, int statusCode) {
        loggedResponseCount++;
        setupErrorLogger();
        errorLogger.info("status:"+statusCode+"; URL:"+url+";");
    }
    private void setupMainLogger() {
        if (fh == null){
            try {
                fh = new FileHandler("log_"+new SimpleDateFormat("yyyy-MM-dd.HH.mm.ss").format(Calendar.getInstance().getTime())+".log");
                logger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupErrorLogger() {
        if (fhError == null) {
            try {
                fhError = new FileHandler("errorLog_" + new SimpleDateFormat("yyyy-MM-dd.HH.mm.ss").format(Calendar.getInstance().getTime()) + ".log");
                errorLogger.addHandler(fhError);
                SimpleFormatter formatter = new SimpleFormatter();
                fhError.setFormatter(formatter);

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupOverviewLogger() {
        if (fhOverview == null){
            try {
                fhOverview = new FileHandler("overviewLog_"+new SimpleDateFormat("yyyy-MM-dd.HH.mm.ss").format(Calendar.getInstance().getTime())+".log");
                overviewLogger.addHandler(fhOverview);
                SimpleFormatter formatter = new SimpleFormatter();
                fhOverview.setFormatter(formatter);

            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getAverageResponseTime() {
        return totalTimeSpent.divide(new BigInteger(String.valueOf(logged200ResponseCount))).toString();
    }

    public String getAverageResponseTimeNewTerms() {
        return totalTimeSpentOnNewTerm.divide(new BigInteger(String.valueOf(logged200ResponseCountNewTerm))).toString();
    }
}
