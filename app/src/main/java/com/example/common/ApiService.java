package com.example.common;

import com.example.common.first.Bean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("search?term=all&media=podcast&limit=10")
    Observable<Bean> getQiNiuToken();

    @GET("search?term=摇滚&media=podcast")
    Observable<Bean> getFox();

    @GET("search?term=民谣&media=podcast")
    Observable<Bean> getStar();
}
