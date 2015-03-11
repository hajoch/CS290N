
/**
 * Created by Kyrre on 09.03.2015.
 */
public class ApacheHttpGet {

    public static void main(String[] args) {

        httpGet("http://localhost:9200/bank/_search?*");
    }

    private static void httpGet(String url) {
        Thread tt = new Thread(new GetData(url));
        tt.start();
    }
}
