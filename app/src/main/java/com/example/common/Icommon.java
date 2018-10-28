package com.example.common;

import library.common.http.result.Result;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Icommon {
    @GET("top250")
    Observable<Result<String>> getQiNiuToken();
}
