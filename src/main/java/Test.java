import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Kyrre on 08.03.2015.
 */
public class Test {

    public static AtomicInteger threadCount = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        while (true){
//            if (threadCount.get() < 1){
//                startInstance();
//                Thread.sleep(1000);
//            }
//        }
        getRequest().get();
        getRequest().get();
        System.out.println("Done");

    }

    public static void startInstance() throws ExecutionException, InterruptedException {
        threadCount.incrementAndGet();
        getRequest().get();
        threadCount.decrementAndGet();
    }

    public static Future<HttpResponse<JsonNode>> getRequest(){
        Future<HttpResponse<JsonNode>> future = Unirest.get("http://localhost:9200/bank/_search?*")
                .header("accept", "application/json")
                .asJsonAsync(new Callback<JsonNode>() {

                    public void failed(UnirestException e) {
                        System.out.println("The request has failed");
                    }

                    public void completed(HttpResponse<JsonNode> response) {
                        //int code = response.getStatus();
                        //Headers headers = response.getHeaders();
                        //JsonNode body = response.getBody();
                        //InputStream rawBody = response.getRawBody();
                        if (response == null){
                            System.out.println("AHHHHHHHHHHHHH FUCK");
                        }
                        System.out.println("The request has been completed");
                    }

                    public void cancelled() {
                        System.out.println("The request has been cancelled");
                    }

                });
        return future;
    }
}
