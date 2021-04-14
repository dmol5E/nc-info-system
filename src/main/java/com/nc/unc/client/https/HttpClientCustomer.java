package com.nc.unc.client.https;
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

    public static Response searchCustomers(String name) throws IOException {
        HttpUrl.Builder httpBuilder =
                HttpUrl.parse(PROTOCOL + HOST + PORT + "/api/customer/search")
                        .newBuilder()
                        .addQueryParameter("name", String.valueOf(name));
        Request request =
                new Request.Builder()
                        .url(httpBuilder.build())
                        .addHeader("host", HOST + PORT)
                        .build();
        return client.newCall(request).execute();
    }

    public static Response createCustomer(String json)  throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, json))
                .url(PROTOCOL + HOST + PORT + "/api/customer")
                .build();
        return client.newCall(request).execute();
    }
}
