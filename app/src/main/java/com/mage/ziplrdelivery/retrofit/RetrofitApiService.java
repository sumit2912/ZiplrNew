package com.mage.ziplrdelivery.retrofit;

import com.google.gson.JsonObject;
import com.mage.ziplrdelivery.model.data.ResponseBean;
import com.mage.ziplrdelivery.model.data.Result;
import com.mage.ziplrdelivery.model.param.LoginParamBean;
import com.mage.ziplrdelivery.model.param.RegistrationParamBean;
import com.mage.ziplrdelivery.api.ApiConst;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitApiService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })

    //signup
    @POST(ApiConst.SIGN_UP)
    Single<Response<ResponseBean>> signUp(@Body RegistrationParamBean registrationParamBean);

    //verify otp
    @POST(ApiConst.VERIFY_OTP)
    Single<Response<ResponseBean>> verifyOtp(@Body Result result);

    //phone check
    @POST(ApiConst.PHONE_CHECK)
    Single<Response<ResponseBean>> phoneCheck(@Body JsonObject jsonObject);

    //send otp
    @POST(ApiConst.SEND_OTP)
    Single<Response<ResponseBean>> sendOTP(@Body JsonObject jsonObject);

    //login
    @POST(ApiConst.LOGIN)
    Single<Response<ResponseBean>> login(@Body LoginParamBean loginParamBean);

    //forgot password
    @POST(ApiConst.FORGOT_PASSWORD)
    Single<Response<ResponseBean>> forgotPassword(@Body LoginParamBean loginParamBean);

    //logout
    @GET(ApiConst.LOGOUT)
    Single<Response<ResponseBean>> logout();
}
