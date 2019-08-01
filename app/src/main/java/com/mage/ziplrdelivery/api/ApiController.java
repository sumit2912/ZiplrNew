package com.mage.ziplrdelivery.api;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.data_model.Result;
import com.mage.ziplrdelivery.data_model.ResultBean;
import com.mage.ziplrdelivery.data_model.ResultListBean;
import com.mage.ziplrdelivery.listener.ApiResponseListener;
import com.mage.ziplrdelivery.listener.ResponseListener;
import com.mage.ziplrdelivery.param_model.RegistrationParamBean;
import com.mage.ziplrdelivery.retrofit.RetrofitApiService;
import com.mage.ziplrdelivery.retrofit.ServiceGenerator;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.utils.constant.ApiConst;

import java.util.List;

import retrofit2.Response;

public class ApiController implements ApiResponseListener {

    private Context caller;
    private ResponseListener responseListener;
    private JsonObject jsonObject;
    private RetrofitApiService jsonPostService;
    private String method, msg;
    private int status;
    private Result result;
    private List<Result> resultList;
    private SingleResponse singleResponse;

    public ApiController(Context caller, ResponseListener responseListener) {
        this.caller = caller;
        this.responseListener = responseListener;
        singleResponse = SingleResponse.getInstance(caller,ApiController.this);
    }

    private void init() {
        jsonObject = new JsonObject();
        jsonPostService = null;
        method = null;
        msg = null;
        status = -1;
        result = null;
        resultList = null;
    }

    public Result getResultData() {
        return this.result;
    }

    public List<Result> getResultListData() {
        return this.resultList;
    }

    private RetrofitApiService getJsonPostService(JsonObject obj) {
        if (obj == null) {
            Utils.print("method = " + method + "   request = " + ApiConst.API_HOST + method + " param = null");
            return ServiceGenerator.createService(RetrofitApiService.class, ApiConst.API_HOST, method);
        } else {
            Utils.print("method = " + method + "   request = " + ApiConst.API_HOST + method + " param = " + obj.toString());
            return ServiceGenerator.createService(RetrofitApiService.class, ApiConst.API_HOST, method, obj);
        }
    }

    public void getApiSignUp(RegistrationParamBean registrationParamBean){
        init();
        method = ApiConst.AUTH_SIGNUP;
        Utils.print(registrationParamBean.printParams());
        jsonPostService = getJsonPostService(null);
        singleResponse.init(method,jsonPostService.signUp(registrationParamBean),false);
    }

    @Override
    public void onSuccessOccur(String method, Response<?> response, boolean isResultListBean) {
        if(response.isSuccessful()){
            if(response.body() instanceof ResultBean){
                Utils.print(ApiController.class.getSimpleName(),"ResultBean Success");
                ResultBean resultBean = (ResultBean) response.body();
                status = resultBean.getStatus();
                msg = resultBean.getMessage();
                result = resultBean.getResult();
            }else if(response.body() instanceof ResultListBean){
                Utils.print(ApiController.class.getSimpleName(),"ResultListBean Success");

            }
            doCallBack();
        }else {
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
                    } else if (status < 0) {
                        Utils.toast(caller, msg, false);
                        responseListener.onResponse(method, ApiConst.API_RESULT.FAIL, status, msg);
                    } else if (status == 0) {
                        Utils.toast(caller, msg, false);
                        responseListener.onResponse(method, ApiConst.API_RESULT.FAIL, status, msg);
                    }
                } catch (Exception e) {
                    Utils.print(this.getClass().getSimpleName() + " ::doCallBack:: Exception :: method = " + method, e);
                }
            }
        });
    }
}
