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

/**
 * Created by Kyrre on 09.03.2015.
 */
public class ApacheHttpGet {

    public static void main(String[] args) {

        httpGet(1);
        httpGet(2);
        httpGet(3);
        httpGet(4);
        httpGet(5);
        httpGet(6);
    }

    private static void httpGet(final int num) {
        Thread tt = new Thread(Runnable t = new Runnable() {
            @Override
            public void run() {
                try {

                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpGet getRequest = new HttpGet(
                            "http://localhost:9200/bank/_search?*");
                    getRequest.addHeader("accept", "application/json");

                    HttpResponse response = httpClient.execute(getRequest);

                    if (response.getStatusLine().getStatusCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + response.getStatusLine().getStatusCode());
                    }

                    BufferedReader br = new BufferedReader(
                            new InputStreamReader((response.getEntity().getContent())));

//                    String output;
//                    System.out.println("Output from Server .... \n");
//                    while ((output = br.readLine()) != null) {
//                        System.out.println(output);
//                    }

                    httpClient.getConnectionManager().shutdown();
                    Thread.sleep(1000*(6-num));
                    System.out.println(""+num);

                } catch (ClientProtocolException e) {

                    e.printStackTrace();

                } catch (IOException e) {

                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        tt.run();
    }
}