package library.common.http.downLoad;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public  abstract class DownLoadObserver implements Observer<DownloadInfo> {
    @Override
    public void onSubscribe(Disposable d) {
        Disposable d1 = d;
    }

    @Override
    public void onNext(DownloadInfo downloadInfo) {
        DownloadInfo downloadInfo1 = downloadInfo;
    }

//    @Override
//    public void onError(Throwable e) {
//        e.printStackTrace();
//    }
}
