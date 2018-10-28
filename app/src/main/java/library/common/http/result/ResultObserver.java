package library.common.http.result;

import android.util.Log;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import library.common.http.Statues;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@SuppressWarnings("unused")
public abstract class ResultObserver<T> implements Observer<Result<T>> {
    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(Result<T> result) {
        Log.d("luchixiang", "121" + 1);
        if (result != null) {
            if (result.getCode() == Statues.NET_CODE_SUCCESS) {
                handlerResult(result.getCode());
            } else {
                handlerError(result.getCode(), result.getMsg());
            }
        } else {
            handlerError(Statues.NET_CODE_ERROR, Statues.EMPTY_RESPONSE_EXCEPTION);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            handlerError(Statues.NET_CODE_SOCKET_TIMEOUT, Statues.SOCKET_TIMEOUT_EXCEPTION);
        } else if (e instanceof ConnectException) {
            handlerError(Statues.NET_CODE_CONNECT, Statues.CONNECT_EXCEPTION);
        } else if (e instanceof UnknownHostException) {
            handlerError(Statues.NET_CODE_UNKNOWN_HOST, Statues.UNKNOWN_HOST_EXCEPTION);
        } else {
            handlerError(Statues.NET_CODE_ERROR, e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void handlerResult(int t);

    public void handlerError(int code, String msg) {
        Log.d("luchixiang", "handlerError: " + code + msg);
    }
}
