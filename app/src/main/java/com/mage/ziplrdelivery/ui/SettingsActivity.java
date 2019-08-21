package com.mage.ziplrdelivery.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.adapter.SettingsMenuAdapter;
import com.mage.ziplrdelivery.databinding.ActivitySettingsBinding;
import com.mage.ziplrdelivery.databinding.LayoutToolbarCommonBinding;
import com.mage.ziplrdelivery.screen.Data;
import com.mage.ziplrdelivery.screen.Screen;
import com.mage.ziplrdelivery.screen.ScreenHelper;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends BaseActivity implements ScreenHelper.DataMessageListener {

    private static final String TAG = Screen.SETTINGS_ACTIVITY;
    private ActivitySettingsBinding binding;
    private LayoutToolbarCommonBinding tbBinding;
    private LinearLayoutManager llManager;
    private SettingsMenuAdapter settingsMenuAdapter;
    private int pos = -1;
    private Intent cmsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUi();
    }

    @Override
    protected Context getContext() {
        return SettingsActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected <S> S getViewBinding(S s) {
        binding = (ActivitySettingsBinding) s;
        tbBinding = binding.toolBarView;
        return (S) binding;
    }

    @Override
    protected ScreenHelper.DataMessageListener addDataMessageListener() {
        return SettingsActivity.this;
    }

    @Override
    protected void initUi() {
        tbBinding.ivBack.setOnClickListener(this);
        llManager = new LinearLayoutManager(mContext);
        binding.rvSettingMenuList.setLayoutManager(llManager);
        settingsMenuAdapter = new SettingsMenuAdapter(getDefaultMenuList(), this);
        binding.rvSettingMenuList.setAdapter(settingsMenuAdapter);
    }

    private List<String> getDefaultMenuList() {
        List<String> list = new ArrayList<>();
        list.add("Change Password");
        list.add("Privacy Policy");
        list.add("Terms and Conditions");
        list.add("About Us");
        return list;
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
        switch (view.getId()) {
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.clMenuItem:
                pos = Integer.parseInt(String.valueOf(view.getTag()));
                if (pos != -1) {
                    if(cmsIntent == null)
                        cmsIntent = new Intent(SettingsActivity.this,CMSActivity.class);
                    VALUE_FROM_ACTIVITY = "Pos = "+pos;
                    cmsIntent.putExtra(KEY_FROM_ACTIVITY,VALUE_FROM_ACTIVITY);
                    startActivity(cmsIntent);
                }
                break;
        }
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

    @Override
    public void onBackPressed() {
        finish();
    }
}
