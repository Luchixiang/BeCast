package library.common.http.interceeptor;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
//参数拦截器
public class ParamterInterceptor implements Interceptor {
    private ArrayMap<String, String> params;
    public ParamterInterceptor(ArrayMap<String, String> params) {
        this.params = params;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        //如果公共请求参数不为空,则构建新的请求
        if (params != null) {
            Request.Builder newRequestBuilder = oldRequest.newBuilder();
            //GET请求则使用HttpUrl.Builder来构建
            if ("GET".equalsIgnoreCase(oldRequest.method())) {
                HttpUrl.Builder httpUrlBuilder = oldRequest.url().newBuilder();
                for (String key : params.keySet()) {
                    httpUrlBuilder.addQueryParameter(key, params.get(key));
                }
                newRequestBuilder.url(httpUrlBuilder.build());
            } else {
                //如果原请求是表单请求
                if (oldRequest.body() instanceof FormBody) {
                    FormBody.Builder formBodyBuilder = new FormBody.Builder();
                    for (String key : params.keySet()) {
                        formBodyBuilder.add(key, params.get(key));
                    }
                    FormBody oldFormBody = (FormBody) oldRequest.body();
                    int size = oldFormBody.size();
                    for (int i = 0; i < size; i++) {
                        formBodyBuilder.add(oldFormBody.name(i), oldFormBody.value(i));
                    }
                    newRequestBuilder.post(formBodyBuilder.build());
                }
                // TODO:  处理其它类型的request.body
            }
            return chain.proceed(newRequestBuilder.build());
        }
        return chain.proceed(oldRequest);
    }
}
