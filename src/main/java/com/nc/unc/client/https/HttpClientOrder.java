package com.nc.unc.client.https;


import com.squareup.okhttp.*;

import java.io.IOException;

public class HttpClientOrder {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":80";

    public static Response getStorage() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/api/order/storage")
                .addHeader("host", HOST + PORT)
                .build();
        return client.newCall(request).execute();
    }

    public static Response getAll() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/api/order")
                .addHeader("host", HOST + PORT)
                .build();
        return client.newCall(request).execute();
    }

    public static Response putOrderItem(int id, int increase) throws IOException {
        HttpUrl.Builder httpBuilder =
                HttpUrl.parse(PROTOCOL + HOST + PORT + "/api/order/storage/put")
                        .newBuilder()
                        .addQueryParameter("id", String.valueOf(id))
                        .addQueryParameter("increase", String.valueOf(increase));
        Request request =
                new Request.Builder()
                        .url(httpBuilder.build())
                        .addHeader("host", HOST + PORT)
                        .build();
        return client.newCall(request).execute();
    }

    public static Response remove(int id) throws IOException {
        HttpUrl.Builder httpBuilder =
                HttpUrl.parse(PROTOCOL + HOST + PORT + "/api/order/storage/rem")
                        .newBuilder()
                        .addQueryParameter("id", String.valueOf(id));
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
                .url(PROTOCOL + HOST + PORT + "/api/order/customer")
                .build();
        return client.newCall(request).execute();
    }

    public static Response createNewOrder(String json)  throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, json))
                .url(PROTOCOL + HOST + PORT + "/api/order/create")
                .build();
        return client.newCall(request).execute();
    }

    public static Response find(int id) throws IOException {
        HttpUrl.Builder httpBuilder =
                HttpUrl.parse(PROTOCOL + HOST + PORT + "/api/order/find")
                        .newBuilder()
                        .addQueryParameter("id", String.valueOf(id));
        Request request =
                new Request.Builder()
                        .url(httpBuilder.build())
                        .addHeader("host", HOST + PORT)
                        .build();
        return client.newCall(request).execute();
    }

    public static Response getSum() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/api/order/storage/sum")
                .addHeader("host", HOST + PORT)
                .build();
        return client.newCall(request).execute();
    }

    public static Response updateOrder(String json) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .post(RequestBody.create(mediaType, json))
                .url(PROTOCOL + HOST + PORT + "/api/order/create")
                .build();
        String result = request.body().toString();
        return client.newCall(request).execute();
    }


    public static Response getCustomer() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/api/order/customer")
                .addHeader("host", HOST + PORT)
                .build();
        return client.newCall(request).execute();
    }
}
