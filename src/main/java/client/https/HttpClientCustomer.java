package client.https;
import com.squareup.okhttp.*;

import java.io.IOException;

public class HttpClientCustomer {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":80";

    public static Response getCustomers() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/api/customer/all")
                .addHeader("host", HOST + PORT)
                .build();
        return client.newCall(request).execute();
    }
}
