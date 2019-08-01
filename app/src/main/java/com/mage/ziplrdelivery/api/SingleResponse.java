package com.mage.ziplrdelivery.api;

import android.content.Context;
import com.mage.ziplrdelivery.listener.ApiResponseListener;
import com.mage.ziplrdelivery.utils.Utils;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class SingleResponse {
    private static SingleResponse singleResponse;
    private Context context;
    private ApiResponseListener apiResponseListener;

    public SingleResponse(Context context, ApiResponseListener apiResponseListener) {
        this.context = context;
        this.apiResponseListener = apiResponseListener;
    }

    public static SingleResponse getInstance(Context context, ApiResponseListener apiResponseListener) {
        if (singleResponse == null) {
            singleResponse = new SingleResponse(context, apiResponseListener);
        }
        return singleResponse;
    }

    public void init(String method, Single<Response<Object>> observable, boolean isResultListBean) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<Object> response) {
                        Utils.print(this.getClass().getSimpleName(),"response.isSuccessful = "+response.isSuccessful());
                        apiResponseListener.onSuccessOccur(method,response, isResultListBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        apiResponseListener.onErrorOccur(method,e);
                    }
                });
    }

}
