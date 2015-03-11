import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Kyrre on 09.03.2015.
 */
public class ApacheHttpGet {
    public static AtomicInteger threadCount = new AtomicInteger(0);
    public static final int MAX_THREAD_COUNT =  10;

    public static final String  IP = "192.168.0.7";

    public static void main(String[] args) {
        while(true){
            if (threadCount.get() < 10){
                httpGet("http://"+IP+":9200/bank/_search?"+getRandomWord(6));
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void httpGet(String url) {
        Thread tt = new Thread(new GetData(url));
        tt.start();
    }

    private static String getRandomWord(int length) {
        String s = "";
        Random r = new Random();
        for(int i =0; i<length; i++) {
            s = s + ((char)r.nextInt(26)+ 'a');
        }
        return s;
    }
}
