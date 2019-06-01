package com.example.rss_app.app.service;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    private static Retrofit instance;

    public static Retrofit getInstance() {

        Service service = new Service();

        if (instance == null) {
            instance = new Retrofit.Builder().baseUrl(service.API_BASE_URL).addConverterFactory(
                    ScalarsConverterFactory.create()
            ).addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
            ).build();
        }

        return instance;
    }
}
