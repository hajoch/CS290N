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
    public static final int MAX_THREAD_COUNT =  10;
    public static ArrayList<String> wordList;
    public static void main(String[] args) {
        wordList = readWordIndex();
        while(true){
            if (threadCount.get() < 100){
                httpGet("http://192.168.0.8:9200/bank/_search?"+getRandomNoun());
            }
            try {
                Thread.sleep(1000);
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
            BufferedReader br = new BufferedReader(new FileReader("test.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                wordList.add(line.split("[-_ ]")[0]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return wordList;
    }

}
