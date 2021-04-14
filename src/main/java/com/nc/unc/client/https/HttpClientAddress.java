package com.nc.unc.client.https;

import com.squareup.okhttp.*;
import java.io.IOException;

public class HttpClientAddress {
    private static final OkHttpClient client = new OkHttpClient();
    private static final String PROTOCOL = "http://";
    private static final String HOST = "localhost";
    private static final String PORT = ":80";

    public static Response getAll() throws IOException {
        Request request = new Request.Builder()
                .get()
                .url(PROTOCOL + HOST + PORT + "/api/address")
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

    public static Response search(String address) throws IOException {
        HttpUrl.Builder httpBuilder =
                HttpUrl.parse(PROTOCOL + HOST + PORT + "/api/address/search/address")
                        .newBuilder()
                        .addQueryParameter("address", String.valueOf(address));
        Request request =
                new Request.Builder()
                        .url(httpBuilder.build())
                        .addHeader("host", HOST + PORT)
                        .build();

        return client.newCall(request).execute();
    }

    public static Response search(int zipcode) throws IOException {
        HttpUrl.Builder httpBuilder =
                HttpUrl.parse(PROTOCOL + HOST + PORT + "/api/address/search/zipcode")
                        .newBuilder()
                        .addQueryParameter("zipcode", String.valueOf(zipcode));
        Request request =
                new Request.Builder()
                        .url(httpBuilder.build())
                        .addHeader("host", HOST + PORT)
                        .build();
        return client.newCall(request).execute();
    }

    public static Response search(int zipcode, String address) throws IOException {
        HttpUrl.Builder httpBuilder =
                HttpUrl.parse(PROTOCOL + HOST + PORT + "/api/address/search")
                        .newBuilder()
                        .addQueryParameter("zipcode", String.valueOf(zipcode))
                        .addQueryParameter("address", String.valueOf(address));

        Request request =
                new Request.Builder()
                        .url(httpBuilder.build())
                        .addHeader("host", HOST + PORT)
                        .build();
        return client.newCall(request).execute();
    }
}
