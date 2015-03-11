import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(
                    url);
            getRequest.addHeader("accept", "application/json");

            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                Log.getInstance().logGetRequestNot200(url, response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String line;
            String output = "";
            System.out.println("Output from Server .... \n");
            while ((line = br.readLine()) != null) {
                output+=line;
            }
            br.close();
            Log.getInstance().logGetRequest(url,response.getStatusLine().getStatusCode(),100, output.length());
            httpClient.getConnectionManager().shutdown();

        } catch (Exception e) {
            e.printStackTrace();
        }
        ApacheHttpGet.threadCount.decrementAndGet();
    }
}
