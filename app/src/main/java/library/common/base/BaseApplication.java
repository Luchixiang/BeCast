package library.common.base;

import android.app.Application;

import library.common.util.Utils;

public  class BaseApplication extends Application {
    private static BaseApplication APPLICATION;
    public static BaseApplication getApplication()
    {
       return APPLICATION;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        APPLICATION =this;
        //初始化工具类
        Utils.init(this);
    }
}
