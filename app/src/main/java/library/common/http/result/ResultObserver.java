package library.common.http.result;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

@SuppressWarnings("unused")
public abstract class ResultObserver<T> implements Observer<BaseResult<T>> {
    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(BaseResult<T> baseResult) {
        if (baseResult != null) {
            if (baseResult.getRequestCode()!=0) {
                handlerResult(baseResult.getData());
            } else {
                handlerError(baseResult.getRequestCode());
            }
        } else {
//            handlerError(Statues.NET_CODE_ERROR, Statues.EMPTY_RESPONSE_EXCEPTION);
        }
    }

    @Override
   public void onError(Throwable e) {
//        if (e instanceof SocketTimeoutException) {
//            handlerError(Statues.NET_CODE_SOCKET_TIMEOUT, Statues.SOCKET_TIMEOUT_EXCEPTION);
//        } else if (e instanceof ConnectException) {
//            handlerError(Statues.NET_CODE_CONNECT, Statues.CONNECT_EXCEPTION);
//        } else if (e instanceof UnknownHostException) {
//            handlerError(Statues.NET_CODE_UNKNOWN_HOST, Statues.UNKNOWN_HOST_EXCEPTION);
//        } else {
//            handlerError(Statues.NET_CODE_ERROR, e.getMessage());
//        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void handlerResult(T t);

    public void handlerError(int code) {
        Log.d("luchixiang", "handlerError: " + code );
    }
}
