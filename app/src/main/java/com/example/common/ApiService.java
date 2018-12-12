package com.example.common;

import com.example.common.first.Top5;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("search?term=all&media=podcast&limit=5")
    Observable<Top5> getQiNiuToken();
}
