package client.https;

import com.nc.unc.dto.ProductDto;
import com.squareup.okhttp.*;

import java.io.IOException;

public class HttpClientProduct {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":80";

    public static Response getProducts() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/api/products")
                .addHeader("host", HOST + PORT)
                .build();
        return client.newCall(request).execute();
    }

    public static Response create(String json) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, json))
                .url(PROTOCOL + HOST + PORT + "/api/products")
                .build();
        return client.newCall(request).execute();
    }

    public static Response searchById(int id) throws IOException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, ""))
                .url(PROTOCOL + HOST + PORT + "/api/products?id=" + id)
                .addHeader("host", HOST + PORT)
                .build();
        return client.newCall(request).execute();
    }

    public static Response increase(int id, int count) throws IOException {
        HttpUrl.Builder httpBuilder =
                HttpUrl.parse(PROTOCOL + HOST + PORT + "/api/products/increase")
                        .newBuilder()
                        .addQueryParameter("id", String.valueOf(id))
                        .addQueryParameter("count", String.valueOf(count));

        Request request =
                new Request.Builder()
                        .url(httpBuilder.build())
                        .addHeader("host", HOST + PORT)
                        .build();
        return client.newCall(request).execute();

    }
}
