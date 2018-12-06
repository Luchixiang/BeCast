package com.example.common;

import com.example.common.first.Top250Bean;
import com.example.common.first.Top5;
import com.example.common.tour.Classify;
import com.example.common.tour.ClassifyList;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("search?term=all&media=podcast&limit=5")
    Observable<Top5> getQiNiuToken();
}
