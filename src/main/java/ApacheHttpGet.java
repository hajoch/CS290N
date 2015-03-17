import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Kyrre on 09.03.2015.
 */
public class ApacheHttpGet {
    public static AtomicInteger threadCount = new AtomicInteger(0);
    public static ArrayList<String> wordList;


    public static final String  IP = "192.168.0.8";
    //public static final String  IP = "localhost";
    public static final int MAX_THREAD_COUNT =  1;
    public static final int OVERVIEW_LOG_INTERVAL = 100; //milliseconds
    public static final int REQUEST_INTERVAL = 100; //milliseconds

    public static void main(String[] args) {
        wordList = readWordIndex();
        while(true){
            if (threadCount.get() < MAX_THREAD_COUNT) {
                httpGet("http://"+IP+":9200/my_river/_search?" + getRandomNoun());
            }
//            if (threadCount.get() < 10){
//                httpGet("http://"+IP+":9200/bank/_search?"+getRandomWord(6));
//            }
            try {
                Thread.sleep(REQUEST_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void httpGet(String url) {
        Thread tt = new Thread(new GetData(url));
        tt.start();
    }

    private static String getRandomNoun(){
        Random r = new Random();
        return wordList.get(r.nextInt(wordList.size()-1));
    }

    private static ArrayList<String> readWordIndex(){
        ArrayList<String> wordList = new ArrayList<String>(117630);
        try {
            BufferedReader br = new BufferedReader(new FileReader("index.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                wordList.add(line.split("[-_ ]")[0]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return wordList;
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
