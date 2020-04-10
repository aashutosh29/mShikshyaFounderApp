package com.bihanitech.shikshyaprasasak.remote;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class RequestHandler {

    public static <T> void asyncTask(Observable<T> call, final RetroReactiveCallBack<T> retroReactiveCallBack){

        Consumer<T> response = new Consumer<T>() {
            @Override
            public void accept(T t) throws Exception {
                retroReactiveCallBack.onComplete(t);
            }
        };



        Consumer<Throwable> error = new Consumer<Throwable>() {
            @Override
            public void accept(Throwable t) throws Exception {
                if (t instanceof HttpException) {
                    HttpException httpException = (HttpException)t;
                    int code = httpException.code();

                    retroReactiveCallBack.onError((HttpException) t,code);


                }else if(t instanceof Exception) {
                    retroReactiveCallBack.onConnectionException((Exception) t);
                }
            }
        };

        call.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response,error);

    }

    public interface RetroReactiveCallBack<T>{
        void onComplete(T response);

        void onError(Exception e, int code);

        void onConnectionException(Exception e);
    }

}
