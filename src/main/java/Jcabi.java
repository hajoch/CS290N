import com.jcabi.http.Request;
import com.jcabi.http.Response;
import com.jcabi.http.request.JdkRequest;

import java.io.IOException;

/**
 * Created by Kyrre on 09.03.2015.
 */
public class Jcabi {

    public static void main(String[] args) throws IOException {
        Request request = new JdkRequest("http://localhost:9200/bank/_search?*");
        Response response = request.fetch();
        int status = response.status();
        String body = response.body();
        System.out.println(status);
        System.out.println(body);
    }
}
