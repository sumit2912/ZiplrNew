package com.mage.ziplrdelivery.api;

import android.content.Context;

import com.mage.ziplrdelivery.data_model.ResponseBean;
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

    public void init(String method, Single<Response<ResponseBean>> observable) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ResponseBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<ResponseBean> response) {
                        apiResponseListener.onSuccessOccur(method, response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        apiResponseListener.onErrorOccur(method, e);
                    }
                });
    }

}
