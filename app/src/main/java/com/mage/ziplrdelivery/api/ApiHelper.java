package com.mage.ziplrdelivery.api;

import com.google.gson.JsonObject;
import com.mage.ziplrdelivery.data_model.ResponseBean;
import com.mage.ziplrdelivery.data_model.Result;
import com.mage.ziplrdelivery.param_model.LoginParamBean;
import com.mage.ziplrdelivery.param_model.RegistrationParamBean;
import com.mage.ziplrdelivery.retrofit.RetrofitApiService;
import com.mage.ziplrdelivery.retrofit.ServiceGenerator;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.utils.constant.ApiConst;

import io.reactivex.Single;
import retrofit2.Response;

public class ApiHelper {

    private static final String TAG = "ApiHelper";

    private static void printParameters(JsonObject jsonObject) {
        Utils.print(TAG +"  Request parameters",jsonObject != null?jsonObject.toString():"no parameters");
    }

    public static Single<Response<ResponseBean>> getApiSignUp(RegistrationParamBean registrationParamBean) {
        printParameters(null);
        return ServiceGenerator.createService(RetrofitApiService.class, ApiConst.API_HOST, ApiConst.SIGN_UP).signUp(registrationParamBean);
    }

    public static Single<Response<ResponseBean>> getApiVerification(Result result) {
        printParameters(null);
        return ServiceGenerator.createService(RetrofitApiService.class, ApiConst.API_HOST, ApiConst.VERIFY_OTP).verifyOtp(result);
    }

    public static Single<Response<ResponseBean>> getApiPhoneCheck(JsonObject jsonObject) {
        printParameters(jsonObject);
        return ServiceGenerator.createService(RetrofitApiService.class, ApiConst.API_HOST, ApiConst.PHONE_CHECK).phoneCheck(jsonObject);
    }

    public static Single<Response<ResponseBean>> getApiSendOTP(JsonObject jsonObject) {
        printParameters(jsonObject);
        return ServiceGenerator.createService(RetrofitApiService.class, ApiConst.API_HOST, ApiConst.SEND_OTP).sendOTP(jsonObject);
    }

    public static Single<Response<ResponseBean>> getApiLogin() {
        printParameters(null);
        return ServiceGenerator.createService(RetrofitApiService.class, ApiConst.API_HOST, ApiConst.LOGIN).login(LoginParamBean.getInstance());
    }

    public static Single<Response<ResponseBean>> getApiForgotPassword(LoginParamBean loginParamBean) {
        printParameters(null);
        return ServiceGenerator.createService(RetrofitApiService.class, ApiConst.API_HOST, ApiConst.FORGOT_PASSWORD).forgotPassword(loginParamBean);
    }

    public static Single<Response<ResponseBean>> getApiLogout(){
        printParameters(null);
        return ServiceGenerator.createService(RetrofitApiService.class, ApiConst.API_HOST, ApiConst.LOGOUT).logout();
    }
}
