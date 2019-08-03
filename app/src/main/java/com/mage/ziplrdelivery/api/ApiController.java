package com.mage.ziplrdelivery.api;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.mage.ziplrdelivery.MyApplication;
import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.data_model.ResponseBean;
import com.mage.ziplrdelivery.data_model.Result;
import com.mage.ziplrdelivery.listener.ApiResponseListener;
import com.mage.ziplrdelivery.listener.ResponseListener;
import com.mage.ziplrdelivery.param_model.RegistrationParamBean;
import com.mage.ziplrdelivery.retrofit.RetrofitApiService;
import com.mage.ziplrdelivery.retrofit.ServiceGenerator;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.utils.constant.ApiConst;
import com.mage.ziplrdelivery.utils.constant.PrefConst;


import io.reactivex.Single;
import retrofit2.Response;

public class ApiController implements ApiResponseListener {

    private Context caller;
    private ResponseListener responseListener;
    private JsonObject jsonObject;
    private RetrofitApiService jsonPostService;
    private String method, msg;
    private int status;
    private Result result;
    private SingleResponse singleResponse;
    private boolean enable0status = true;

    public ApiController(Context caller, ResponseListener responseListener) {
        this.caller = caller;
        this.responseListener = responseListener;
        singleResponse = SingleResponse.getInstance(caller);
    }

    private void init() {
        jsonObject = new JsonObject();
        jsonPostService = null;
        method = null;
        msg = null;
        status = -1;
        result = null;
        enable0status = true;
    }

    public Result getResultData() {
        return this.result;
    }

    private RetrofitApiService getJsonPostService(JsonObject obj) {
        if (obj == null) {
            Utils.print("method = " + method + "   request = " + ApiConst.API_HOST + method + " param = null");
        } else {
            Utils.print("method = " + method + "   request = " + ApiConst.API_HOST + method + " param = " + obj.toString());
        }
        return ServiceGenerator.createService(RetrofitApiService.class, ApiConst.API_HOST, method);
    }

    public void getApiSignUp(RegistrationParamBean registrationParamBean) {
        init();
        method = ApiConst.AUTH_SIGNUP;
        registrationParamBean.printParams();
        jsonPostService = getJsonPostService(null);
        Single<Response<ResponseBean>> signUpObservable = jsonPostService.signUp(registrationParamBean);
        singleResponse.init(method, signUpObservable, ApiController.this);
    }

    public void getApiVerification(Result result) {
        init();
        method = ApiConst.AUTH_VERIFY_OTP;
        result.printVerifyOtpParams();
        jsonPostService = getJsonPostService(null);
        singleResponse.init(method, jsonPostService.verifyOtp(result), ApiController.this);
    }

    public void set0status(boolean enable) {
        enable0status = enable;
    }

    @Override
    public void onSuccessOccur(String method, Response<ResponseBean> response) {
        if (response.isSuccessful()) {
            Utils.print(ApiController.class.getSimpleName(), "ResponseBean Success");
            ResponseBean responseBean = response.body();
            status = responseBean.getStatus();
            msg = responseBean.getMessage();
            result = responseBean.getResult();
            if(responseBean.getAuthToke() != null){
                MyApplication.getAppManager().prefSetStringValue(PrefConst.PREF_ACCESS_TOKEN,responseBean.getAuthToke().getAccessToken());
            }
            Utils.print("status = " + status + "    msg = " + msg);
        } else {
            msg = "We are upgrading our server";
        }
        doCallBack();
    }

    @Override
    public void onErrorOccur(String method, Throwable e) {
        status = -2;
        msg = caller.getResources().getString(R.string.temporary_server_down);
        Utils.print("::::::::::::::::::::doPostRequest::::::::::onFailure::::::::::::::::::::::::::::::" + e.getMessage());
        doCallBack();
        try {
            Log.e("response-failure", "method = " + method + " Cause = " + e.getCause().getMessage());
            Utils.print(this.getClass() + " :: Exception :: ", e.getLocalizedMessage());
            StackTraceElement[] elements = e.getStackTrace();
            for (int i = 0; i < elements.length; i++) {
                Utils.print(this.getClass().getSimpleName(), elements[i].toString());
            }
        } catch (Exception exception) {
            Utils.print(this.getClass().getSimpleName(), exception);
        }
    }

    private void doCallBack() {
        ((Activity) caller).runOnUiThread(new Runnable() {

            @Override
            public void run() {

                try {
                    if (status == 1) {
                        responseListener.onResponse(method, ApiConst.API_RESULT.SUCCESS, status, msg);
                        Utils.print("responseListener == 1 = " + status);
                    } else if (status < 0) {
                        Utils.toast(caller, msg, false);
                        responseListener.onResponse(method, ApiConst.API_RESULT.FAIL, status, msg);
                        Utils.print("responseListener < 0 = " + status);
                    } else if (status == 0) {
                        if(enable0status) {
                            Utils.toast(caller, msg, false);
                        }
                        responseListener.onResponse(method, ApiConst.API_RESULT.FAIL, status, msg);
                        Utils.print("responseListener == 0 = " + status);
                    }else if(status == 2){
                        Utils.showSessionDialog(caller);
                        responseListener.onResponse(method, ApiConst.API_RESULT.FAIL, status, msg);
                        Utils.print("responseListener == 2 = " + status);
                    }else {
                        responseListener.onResponse(method, ApiConst.API_RESULT.FAIL, status, msg);
                        Utils.print("responseListener >2 = " + status);
                    }
                } catch (Exception e) {
                    Utils.print(this.getClass().getSimpleName() + " ::doCallBack:: Exception :: method = " + method, e);
                }
            }
        });
    }
}
