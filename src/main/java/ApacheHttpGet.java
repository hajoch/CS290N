import com.jcabi.http.Request;
import com.jcabi.http.Response;
import com.jcabi.http.request.ApacheRequest;
import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.response.JsonResponse;
import org.apache.http.HttpHeaders;
import com.jcabi.http.Request;
import com.jcabi.http.request.ApacheRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Kyrre on 09.03.2015.
 */
public class ApacheHttpGet {
    public static AtomicInteger threadCount = new AtomicInteger(0);
    public static final int MAX_THREAD_COUNT =  10;
    public static void main(String[] args) {
        while(true){
            if (threadCount.get() < 10){
                httpGet("http://localhost:9200/bank/_search?*");
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
}
