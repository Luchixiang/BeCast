package library.common;

import android.os.Build;
import android.support.v4.util.ArrayMap;

import java.util.concurrent.TimeUnit;

import library.common.http.interceeptor.HeadInterceptor;
import library.common.http.interceeptor.ParamterInterceptor;
import library.common.util.SystemUtil;
import library.common.util.Utils;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static String getBaseUrl() {
        return "https://itunes.apple.com/";
    }

    private static Cache getCache() {
        return new Cache(Utils.getContext().getCacheDir(), 1024 * 1024 * 50);
    }

    private static ArrayMap<String, String> getRequestHeader() {
        ArrayMap<String, String> header = new ArrayMap<>();
        header.put("app_version", SystemUtil.getVersionName());
        header.put("app_build", "" + SystemUtil.getVersionCode());
        header.put("device_name", Build.MODEL);
        header.put("device_platform", "Android");
        return header;
    }

    static ArrayMap<String, String> getRequestParams() {
        return null;
    }

    public static Retrofit getInstance() {
        return Instance.retrofit;
    }

    private static class Instance {
        private static Retrofit retrofit = getRetrofit();

        private static Retrofit getRetrofit() {
            OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
            okHttpBuilder.addInterceptor(new HeadInterceptor(getRequestHeader()));
            okHttpBuilder.addInterceptor(new ParamterInterceptor(getRequestParams()));
            okHttpBuilder.cache(getCache());
            //设置网络连接失败时自动重试
            okHttpBuilder.retryOnConnectionFailure(true);
            //设置连接超时
            okHttpBuilder.connectTimeout(40, TimeUnit.SECONDS);
            //设置写超时
            okHttpBuilder.writeTimeout(40, TimeUnit.SECONDS);
            //设置读超时
            okHttpBuilder.readTimeout(40, TimeUnit.SECONDS);
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
            retrofitBuilder.baseUrl(getBaseUrl());
            retrofitBuilder.client(okHttpBuilder.build());
            retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
            return retrofitBuilder.build();
        }
    }

}
