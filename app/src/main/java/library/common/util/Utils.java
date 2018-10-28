package library.common.util;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import io.reactivex.annotations.NonNull;

public class Utils {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(@NonNull Context context) {
        Utils.mContext = context.getApplicationContext();
    }

    public static Context getContext() {
        if (mContext != null) return mContext;
        throw new NullPointerException("u should init first");
    }

    public static String getAppName() {
        try {
            PackageManager pm = Utils.getContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), 0);
            return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
