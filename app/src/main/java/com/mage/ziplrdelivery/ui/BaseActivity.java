package com.mage.ziplrdelivery.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.textfield.TextInputEditText;
import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.api.ApiController;
import com.mage.ziplrdelivery.common.AlertDialogManager;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.listener.ResponseListener;
import com.mage.ziplrdelivery.model.SingletonFactory;
import com.mage.ziplrdelivery.prefmanager.PrefManager;
import com.mage.ziplrdelivery.screen.ScreenHelper;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.api.ApiConst;

import java.util.Objects;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener, ResponseListener, Utils.InternetCheck.NetListener, AlertDialogManager.AlertDialogListener {

    protected static final String TYPE_NOT_REGISTERED = "TYPE_NOT_REGISTERED";
    protected static final String TYPE_NOT_VERIFIED = "TYPE_NOT_VERIFIED";
    protected static final int PROGRESS_TAG_0 = 0;
    protected static final int PROGRESS_TAG_1 = 1;
    protected static final int PROGRESS_TAG_2 = 2;
    protected static final String KEY_FROM_ACTIVITY = "KEY_FROM_ACTIVITY";
    protected static final String KEY_FP_CLICK = "KEY_FP_CLICK";
    protected static final String KEY_RESULT_BEAN = "KEY_RESULT_BEAN";
    protected static final String KEY_PHONE_NO = "KEY_PHONE_NO";
    protected final Handler handler = new Handler();
    protected String VALUE_FROM_ACTIVITY = null;
    protected boolean VALUE_FP_CLICK = false;
    protected boolean isInternet = false;
    protected AppManager appManager;
    protected PrefManager prefManager;
    protected ScreenHelper screenHelper;
    protected SingletonFactory singletonFactory;
    protected Context mContext;
    protected boolean disableClick;
    protected Intent dataIntent;
    protected ApiController apiController;
    protected AlertDialogManager adManager;
    protected ProgressBar progressBar;
    private ViewDataBinding mBinding;
    private LocalBroadcastManager localBroadcastManager;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            new Utils.InternetCheck(BaseActivity.this).execute();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mBinding = DataBindingUtil.setContentView((Activity) mContext, getLayoutId());
        getViewBinding(mBinding);
        appManager = AppManager.getInstance(mContext);
        prefManager = appManager.getPrefManager();
        screenHelper = appManager.getScreenHelper();
        singletonFactory = SingletonFactory.getInstance();
        screenHelper.addActivity((AppCompatActivity) getContext());
        screenHelper.addDataMessageListener(getContext().getClass().getSimpleName(), addDataMessageListener());
        localBroadcastManager = LocalBroadcastManager.getInstance(mContext);
        localBroadcastManager.registerReceiver(broadcastReceiver, new IntentFilter("isInternet"));
        new Utils.InternetCheck(BaseActivity.this).execute();
        dataIntent = getIntent();
        apiController = new ApiController(mContext, this);
        adManager = new AlertDialogManager(mContext, "", "", this);
        ConstraintLayout cl = mBinding.getRoot().findViewById(R.id.mainCL);
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_progress_bar, cl, true);
        progressBar = view.findViewById(R.id.pb);
        if(progressBar.getParent() != null) {
            ((ViewGroup)progressBar.getParent()).removeView(progressBar);
        }
        cl.addView(progressBar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        disableClick = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }

    protected abstract Context getContext();

    protected abstract int getLayoutId();

    protected abstract <S> S getViewBinding(S s);

    protected abstract ScreenHelper.DataMessageListener addDataMessageListener();

    protected abstract void initUi();

    protected abstract void onInternetChange(boolean isInternet);

    protected abstract void callApi(int tag);

    protected abstract void enableScreen(boolean enable);

    @Override
    public void onNetChange(boolean isInternet) {
        this.isInternet = isInternet;
        onInternetChange(isInternet);
    }

    protected String getEdValue(TextInputEditText ed) {
        return Objects.requireNonNull(ed.getText()).toString().trim();
    }

    protected String getResString(int resId) {
        return mContext.getResources().getString(resId);
    }

    protected float getResdimen(int resId) {
        return mContext.getResources().getDimension(resId);
    }

    protected void showProgressBar(boolean show) {
            if (show) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }
    }

    @Override
    public void onResponse(String tag, ApiConst.API_RESULT result, int status, String msg) {
        Utils.print(((AppCompatActivity)getContext()).getClass().getSimpleName(), "tag = " + tag + " result = " + result + " status = " + status + " msg = " + msg);
    }
}
