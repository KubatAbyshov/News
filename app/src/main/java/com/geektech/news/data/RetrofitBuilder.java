package com.geektech.news.data;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {

    private static RetrofitService service;
    private static OkHttpClient client;

    public static RetrofitService getService() {

        if (service == null) {
            service = buildRetrofit();
        }
        return service;

    }

    private static RetrofitService buildRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://data.fixer.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build()
                .create(RetrofitService.class);
    }

    private static OkHttpClient getClient() {
        if (client == null)
            client = buildClient();
        return client;
    }

    private static OkHttpClient buildClient() {
        return new OkHttpClient.Builder()
                .callTimeout(5, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();

    }

}
