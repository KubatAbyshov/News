package com.geektech.news.data;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("latest")
    Call<JsonObject> getCurrencies(@Query("access_key") String key);
}
