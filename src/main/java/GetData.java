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
                System.out.println("Something went wrong, status code: "+response.getStatusLine().getStatusCode()+ " Url: "+url);
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String line;
            String output = "";
            System.out.println("Output from Server .... \n");
            while ((line = br.readLine()) != null) {
                output+=line;
            }
            System.out.println(url);
            System.out.println(output);

            httpClient.getConnectionManager().shutdown();

        } catch (ClientProtocolException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();
        }
        ApacheHttpGet.threadCount.decrementAndGet();
    }
}
