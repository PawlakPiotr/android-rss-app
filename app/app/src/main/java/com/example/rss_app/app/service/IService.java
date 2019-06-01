package com.example.rss_app.app.service;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IService {

    @POST("/api/user/signup")
    @FormUrlEncoded
    Observable<String> register(@Field("email") String email, @Field("password") String password);

    @POST("/api/user/login")
    @FormUrlEncoded
    Observable<String> login(@Field("email") String email, @Field("password") String password);

    @POST("/api/news/favourites")
    @FormUrlEncoded
    Observable<String> favourites(@Field("email") String email);

    @POST("/api/news/favourites/add")
    @FormUrlEncoded
    Observable<String> addToFavourites(@Field("email") String email,
                                       @Field("title") String title,
                                       @Field("description") String desc,
                                       @Field("date") String date);
}
