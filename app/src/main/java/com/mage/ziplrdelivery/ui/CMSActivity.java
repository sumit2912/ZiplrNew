package com.mage.ziplrdelivery.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.databinding.ActivityCmsBinding;
import com.mage.ziplrdelivery.screen.Data;
import com.mage.ziplrdelivery.screen.Screen;
import com.mage.ziplrdelivery.screen.ScreenHelper;

public class CMSActivity extends BaseActivity implements ScreenHelper.DataMessageListener {

    private static final String TAG = Screen.CMS_ACTIVITY;
    private ActivityCmsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(dataIntent.hasExtra(KEY_FROM_ACTIVITY)){
            utils.toast(mContext,dataIntent.getStringExtra(KEY_FROM_ACTIVITY),false);
        }
        initUi();
    }

    @Override
    protected Context getContext() {
        return CMSActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cms;
    }

    @Override
    protected <S> S getViewBinding(S s) {
        binding = (ActivityCmsBinding) s;
        return (S) binding;
    }

    @Override
    protected ScreenHelper.DataMessageListener addDataMessageListener() {
        return CMSActivity.this;
    }

    @Override
    protected void initUi() {

    }

    @Override
    protected void onInternetChange(boolean isInternet) {
        if (isInternet) {
            binding.rlInternet.setVisibility(View.GONE);
        } else {
            binding.rlInternet.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void callApi(int tag) {

    }

    @Override
    protected void enableScreen(boolean enable) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onNegativeClicked(String type) {

    }

    @Override
    public void onPositiveClicked(String type) {

    }

    @Override
    public void onNewDataMessage(String from, String msg, Data data) {

    }
}
