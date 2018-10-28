package library.common.http.interceeptor;

import android.support.v4.util.ArrayMap;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
//头部拦截器
public class HeadInterceptor implements Interceptor {
    private ArrayMap<String, String> headers;
    public HeadInterceptor(ArrayMap<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        //如果公共请求头不为空,则构建新的请求
        if (headers != null) {
            for (String key : headers.keySet()) {
                requestBuilder.addHeader(key, headers.get(key));
            }
        }
        Request request = requestBuilder.build();
        Response.Builder responseBuilder = chain.proceed(request).newBuilder();
        if (!request.cacheControl().toString().isEmpty()) {
            responseBuilder
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, max-age=" + request.cacheControl().maxAgeSeconds());
        }
        return responseBuilder.build();
    }
}
