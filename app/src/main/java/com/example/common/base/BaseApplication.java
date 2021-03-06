package com.example.common.base;

import android.app.Application;

import com.allen.library.RxHttpUtils;
import com.allen.library.config.OkHttpConfig;
import com.allen.library.cookie.store.SPCookieStore;
import com.lzx.starrysky.manager.MusicManager;
import com.lzx.starrysky.notification.NotificationConstructor;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import library.common.util.Utils;
import okhttp3.OkHttpClient;

public class BaseApplication extends Application {
    private final Map<String, Object> headerMaps = new HashMap<>();

    private static BaseApplication APPLICATION;
    public static IWXAPI mWxApi;

    public static BaseApplication getApplication() {
        return APPLICATION;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        APPLICATION = this;
        //初始化工具类
        Utils.init(this);
        //音乐播放集成
        MusicManager.initMusicManager(this);
        NotificationConstructor constructor = new NotificationConstructor.Builder()
                .bulid();
        MusicManager.getInstance().setNotificationConstructor(constructor);
        OkHttpClient okHttpClient = new OkHttpConfig
                .Builder(this)
                //全局的请求头信息
                .setHeaders(headerMaps)
                .setCookieType(new SPCookieStore(this))
                .setReadTimeout(10)
                //全局超时配置
                .setWriteTimeout(10)
                //全局超时配置
                .setConnectTimeout(40)
                //全局是否打开请求log日志
                .setDebug(false)
                .build();

        RxHttpUtils
                .getInstance()
                .init(this)
                .config()
                //配置全局baseUrl
                .setBaseUrl("https://itunes.apple.com/")
                //开启全局配置
                .setOkClient(okHttpClient);

//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("font/NotoSansSC-Regular.otf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );
        registToWX();
    }

    private void registToWX() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, "wx7cb922e9f38b476e", false);
        // 将该app注册到微信
        mWxApi.registerApp("wx7cb922e9f38b476e");
    }
}
