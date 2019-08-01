package com.mage.ziplrdelivery.retrofit;

import com.mage.ziplrdelivery.MyApplication;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.utils.constant.ApiConst;
import com.mage.ziplrdelivery.utils.constant.PrefConst;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static HttpLoggingInterceptor interceptor;
    private static OkHttpClient.Builder httpClient;

    public static <S> S createService(Class<S> serviceClass, String baseUrl, String api) {
        if (interceptor == null) {
            interceptor = new HttpLoggingInterceptor();
        }
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (httpClient == null) {
            httpClient = new OkHttpClient.Builder();
        }
        httpClient.addNetworkInterceptor(interceptor);
        if (!check(api)) {
            Utils.print("Access Token   ::::  " + MyApplication.getAppManager().prefGetStringValue(PrefConst.PREF_ACCESS_TOKEN));
            httpClient.addInterceptor(chain -> {
                Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + MyApplication.getAppManager().prefGetStringValue(PrefConst.PREF_ACCESS_TOKEN)).build();
                return chain.proceed(request);
            });
        }

        Retrofit builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        return builder.create(serviceClass);
    }

    private static boolean check(String api) {
        return api.equals(ApiConst.AUTH_SIGNUP);
    }
}
