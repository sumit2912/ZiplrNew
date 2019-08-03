package com.mage.ziplrdelivery.retrofit;

import com.google.gson.JsonObject;
import com.mage.ziplrdelivery.data_model.ResponseBean;
import com.mage.ziplrdelivery.param_model.RegistrationParamBean;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitApiService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })

    //signup
    @POST("auth/signup")
    Single<Response<ResponseBean>> signUp(@Body RegistrationParamBean registrationParamBean);

    //otp
    @POST("auth/verify/otp")
    Single<Response<ResponseBean>> verifyOtp(@Body RegistrationParamBean registrationParamBean);
}
