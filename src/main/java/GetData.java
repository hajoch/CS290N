import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Kyrre on 10.03.2015.
 */
public class GetData implements Runnable {

    private final String url;

    public GetData(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        ApacheHttpGet.threadCount.incrementAndGet();

        System.out.println("Thread count: "+ApacheHttpGet.threadCount.get());
        try {
            HttpGet getRequest = new HttpGet(
                    url);
            getRequest.addHeader("accept", "application/json");

            long startTime = System.nanoTime();
            HttpResponse response = HttpClientPool.getClient().execute(getRequest);
            long endTime = System.nanoTime();
            int responseTime = (int) ((endTime - startTime)/1000000);  //divide by 1000000 to get milliseconds.

            if (response.getStatusLine().getStatusCode() != 200) {
                Log.getInstance().logGetRequestNot200(url, response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String line;
            String output = "";
            while ((line = br.readLine()) != null) {
                output+=line;
            }
            br.close();
            Log.getInstance().logGetRequest(url, response.getStatusLine().getStatusCode(), responseTime, output.length());

        } catch (Exception e) {
            e.printStackTrace();
        }
        ApacheHttpGet.threadCount.decrementAndGet();
    }
}
