package com.mage.ziplrdelivery.api;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.mage.ziplrdelivery.MyApplication;
import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.data_model.ResponseBean;
import com.mage.ziplrdelivery.data_model.Result;
import com.mage.ziplrdelivery.listener.ResponseListener;
import com.mage.ziplrdelivery.param_model.LoginParamBean;
import com.mage.ziplrdelivery.param_model.RegistrationParamBean;
import com.mage.ziplrdelivery.retrofit.RetrofitApiService;
import com.mage.ziplrdelivery.retrofit.ServiceGenerator;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.utils.constant.ApiConst;
import com.mage.ziplrdelivery.utils.constant.PrefConst;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class ApiController {

    private static final String TAG = "ApiController";
    private Context caller;
    private ResponseListener responseListener;
    private String method, msg;
    private int status;
    private Result result;
    private boolean enable0status = true;
    private JsonObject jsonObject;
    private Single<Response<ResponseBean>> observable;

    public ApiController(Context caller, ResponseListener responseListener) {
        this.caller = caller;
        this.responseListener = responseListener;
    }

    private void init() {
        method = null;
        msg = null;
        status = -1;
        result = null;
        enable0status = true;
        jsonObject = new JsonObject();
        observable = null;
    }

    public Result getResultData() {
        return this.result;
    }

    public void getApiSignUp(RegistrationParamBean registrationParamBean) {
        init();
        method = ApiConst.SIGN_UP;
        registrationParamBean.printParams();
        registrationParamBean.setCountry_code("+91");  //========================== temp=====================================
        observable = ApiHelper.getApiSignUp(registrationParamBean);
        executeApi();
    }

    public void getApiVerification(Result result) {
        init();
        method = ApiConst.VERIFY_OTP;
        result.printVerifyOtpParams();
        observable = ApiHelper.getApiVerification(result);
        executeApi();
    }

    public void getApiPhoneCheck(LoginParamBean loginParamBean) {
        init();
        method = ApiConst.PHONE_CHECK;
        jsonObject.addProperty("phone_number", loginParamBean.getPhone_number());
        observable = ApiHelper.getApiPhoneCheck(jsonObject);
        executeApi();
    }

    public void getApiSendOTP(String phone_number) {
        init();
        method = ApiConst.SEND_OTP;
        jsonObject.addProperty("phone_number", phone_number);
        observable = ApiHelper.getApiSendOTP(jsonObject);
        executeApi();
    }

    public void getApiLogin() {
        init();
        method = ApiConst.LOGIN;
        LoginParamBean.getInstance().printParams();
        observable = ApiHelper.getApiLogin();
        executeApi();
    }

    public void getApiForgotPassword(LoginParamBean loginParamBean) {
        init();
        method = ApiConst.FORGOT_PASSWORD;
        loginParamBean.printParams();
        observable = ApiHelper.getApiForgotPassword(loginParamBean);
        executeApi();
    }

    public void getApiLogout() {
        init();
        method = ApiConst.LOGOUT;
        observable = ApiHelper.getApiLogout();
        executeApi();
    }

    public void set0status(boolean enable) {
        enable0status = enable;
    }

    private void executeApi() {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ResponseBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<ResponseBean> response) {
                        if (response.isSuccessful()) {
                            Utils.print(TAG, "ResponseBean Success");
                            ResponseBean responseBean = response.body();
                            status = responseBean.getStatus();
                            msg = responseBean.getMessage();
                            result = responseBean.getResult();
                            if (result.getOtp() != null) {
                                Utils.print(TAG, "OTP = " + result.getOtp());
                            }
                            if (responseBean.getAuthToke() != null) {
                                MyApplication.getAppManager().prefSetStringValue(PrefConst.PREF_ACCESS_TOKEN, responseBean.getAuthToke().getAccessToken());
                                Utils.print(TAG, "Generated Access Token   ::::  " +
                                        responseBean.getAuthToke().getTokenType() + " " + MyApplication.getAppManager().prefGetStringValue(PrefConst.PREF_ACCESS_TOKEN));
                            }
                            if(result.getUserProfile() != null){

                            }
                            Utils.print("status = " + status + "    msg = " + msg);
                        } else {
                            msg = "We are upgrading our server";
                        }
                        doCallBack();
                    }

                    @Override
                    public void onError(Throwable e) {
                        status = -2;
                        msg = caller.getResources().getString(R.string.temporary_server_down);
                        Utils.print("::::::::::::::::::::doPostRequest::::::::::onFailure::::::::::::::::::::::::::::::" + e.getMessage());
                        doCallBack();
                        try {
                            Utils.print(this.getClass() + " :: Exception :: ", e.getLocalizedMessage());
                            StackTraceElement[] elements = e.getStackTrace();
                            for (int i = 0; i < elements.length; i++) {
                                Utils.print(this.getClass().getSimpleName(), elements[i].toString());
                            }
                        } catch (Exception exception) {
                            Utils.print(this.getClass().getSimpleName(), exception);
                        }
                    }
                });
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
                        if (enable0status) {
                            Utils.toast(caller, msg, false);
                        }
                        responseListener.onResponse(method, ApiConst.API_RESULT.FAIL, status, msg);
                        Utils.print("responseListener == 0 = " + status);
                    } else if (status == 2) {
                        Utils.showSessionDialog(caller);
                        responseListener.onResponse(method, ApiConst.API_RESULT.FAIL, status, msg);
                        Utils.print("responseListener == 2 = " + status);
                    } else {
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
