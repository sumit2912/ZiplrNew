package com.mage.ziplrdelivery.retrofit;

import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static <S> S createService(Class<S> serviceClass, String baseUrl, String api, JsonObject jsonObject) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addNetworkInterceptor(interceptor);
        /*if (!api.equals(Const.AUTH_VERIFY_OTP) && !api.equals(Const.AUTH_LOGIN) && !api.equals(Const.AUTH_SIGNUP) && !api.equals(Const.AUTH_SEND_OTP) && !api.equals(Const.AUTH_RESET_PASSWORD) && !api.equals(Const.AUTH_GET_PAGE) && !api.equals(Const.AUTH_ETHNIC_BG)) {
            Utils.print("Token   ::::  " + Pref.getStringValue(MyApplication.getAppContext(), Const.PREF_ACCESS_TOKEN, ""));
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + Pref.getStringValue(MyApplication.getAppContext(), Const.PREF_ACCESS_TOKEN, "")).build();
                    return chain.proceed(request);
                }
            });
        }*/

        Retrofit builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();
        return builder.create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, String baseUrl, String api) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addNetworkInterceptor(interceptor);
        /*if (!api.equals(Const.AUTH_VERIFY_OTP) && !api.equals(Const.AUTH_LOGIN) && !api.equals(Const.AUTH_SIGNUP) && !api.equals(Const.AUTH_SEND_OTP) && !api.equals(Const.AUTH_RESET_PASSWORD) && !api.equals(Const.AUTH_GET_PAGE) && !api.equals(Const.AUTH_ETHNIC_BG)) {
            Utils.print("Token   ::::  " + Pref.getStringValue(MyApplication.getAppContext(), Const.PREF_ACCESS_TOKEN, ""));
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + Pref.getStringValue(MyApplication.getAppContext(), Const.PREF_ACCESS_TOKEN, "")).build();
                    return chain.proceed(request);
                }
            });
        }*/

        Retrofit builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        return builder.create(serviceClass);
    }
}
