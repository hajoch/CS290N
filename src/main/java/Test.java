import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Created by Kyrre on 08.03.2015.
 */
public class Test {

    public static void main(String[] args) throws UnirestException {
        HttpResponse<JsonNode> jsonResponse = Unirest.get("http://localhost:9200/bank/_search?*").header("accept", "application/json").asJson();

        System.out.println(jsonResponse.getBody());
    }
}
