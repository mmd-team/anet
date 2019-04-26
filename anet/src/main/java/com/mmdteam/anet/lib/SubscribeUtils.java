package com.mmdteam.anet.lib;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SubscribeUtils {
    /**
     * 设置订阅和所在线程环境
     */
    public static <T> void toSubscribe(Observable<T> observable, DisposableObserver<T> disposableObserver) {
        toSubscribe(observable, disposableObserver, 0);
    }

    /**
     * 设置订阅和所在线程环境
     */
    public static <T> void toSubscribe(Observable<T> observable, DisposableObserver<T> disposableObserver, int retry) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(retry)
                .subscribe(disposableObserver);
    }
}
