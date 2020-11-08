package com.lisap.equus.data.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static RetrofitManager instance = null;
    public static final String BASE_URL = "https://fcm.googleapis.com/";
    private RetrofitEndpoints service;

    public static RetrofitManager getInstance() {
        if (instance == null) {
            instance = new RetrofitManager();
        }

        return instance;
    }

    private RetrofitManager() {
        buildRetrofit();
    }

    private void buildRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(RetrofitEndpoints.class);
    }

    public RetrofitEndpoints getService() {
        return this.service;
    }
}
