package com.mage.ziplrdelivery.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.ScaleAnimation;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.adapter.NavigationMenuAdapter;
import com.mage.ziplrdelivery.databinding.LayoutMapViewBinding;
import com.mage.ziplrdelivery.databinding.LayoutNavigationViewBinding;
import com.mage.ziplrdelivery.model.data.DashBoardBean;
import com.mage.ziplrdelivery.screen.Data;
import com.mage.ziplrdelivery.screen.Screen;
import com.mage.ziplrdelivery.model.data.Result;
import com.mage.ziplrdelivery.databinding.ActivityDashBoardBinding;
import com.mage.ziplrdelivery.screen.ScreenHelper;
import com.mage.ziplrdelivery.api.ApiConst;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.viewmodelfactory.viewmodel.DashBoardViewModel;
import com.mage.ziplrdelivery.viewmodelfactory.viewmodel.NavigationMenuViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DashBoardActivity extends BaseActivity implements ScreenHelper.DataMessageListener, Observer<DashBoardBean> {

    private static final String TAG = Screen.DASH_BOARD_ACTIVITY;
    private ActivityDashBoardBinding binding;
    private LayoutMapViewBinding mapBinding;
    private LayoutNavigationViewBinding navBinding;
    private DashBoardViewModel dashBoardViewModel;
    private NavigationMenuViewModel navigationMenuViewModel;
    private RecyclerView rvNavMenuList;
    private LinearLayoutManager llManager;
    private List<NavigationMenuViewModel.NavMenu> navMenuList;
    private NavigationMenuAdapter menuAdapter;
    private DashBoardBean dashBoardBean;
    private ScaleAnimation scaleAnimation;
    private long animDuration = 250;
    private int navItemPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashBoardViewModel = viewModelFactory.create(DashBoardViewModel.class);
        navigationMenuViewModel = viewModelFactory.create(NavigationMenuViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setDashBoardViewModel(dashBoardViewModel);
        navBinding.setLifecycleOwner(this);
        navBinding.setNavigationMenuViewModel(navigationMenuViewModel);
        dashBoardViewModel.getDashBoardMutableLiveData().observe(this, this);
        navigationMenuViewModel.getNavigationMenuMutableLiveData().observe(this, this);
        singletonFactory.getLoginParamBean().resetAll();
        dashBoardBean = singletonFactory.getDashBoardBean();
        dashBoardBean.setProfileName(utils.getLoginData().getName());
        dashBoardBean.setProfileEmail(utils.getLoginData().getEmail());
        singletonFactory.setDashBoardBean(dashBoardBean);
        navigationMenuViewModel.ProfName.setValue(dashBoardBean.getProfileName());
        navigationMenuViewModel.ProfEmail.setValue(dashBoardBean.getProfileEmail());
        initUi();
    }

    @Override
    protected Context getContext() {
        return DashBoardActivity.this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dash_board;
    }

    @Override
    protected <S> S getViewBinding(S s) {
        binding = (ActivityDashBoardBinding) s;
        mapBinding = binding.mapView;
        navBinding = binding.navigationView;
        return (S) binding;
    }

    @Override
    protected ScreenHelper.DataMessageListener addDataMessageListener() {
        return DashBoardActivity.this;
    }

    @Override
    protected void initUi() {
        binding.nonClickable.setOnClickListener(null);
        mapBinding.ivNav.setOnClickListener(this);
        navBinding.ivNavClose.setOnClickListener(this);

        //Navigation Menu
        rvNavMenuList = navBinding.rvNavMenuList;
        llManager = new LinearLayoutManager(mContext);
        rvNavMenuList.setLayoutManager(llManager);
        navMenuList = new ArrayList<>();
        navMenuList = populateNavMenu();
        menuAdapter = new NavigationMenuAdapter(mContext, this, navMenuList);
        rvNavMenuList.setAdapter(menuAdapter);

    }

    private List<NavigationMenuViewModel.NavMenu> populateNavMenu() {
        List<NavigationMenuViewModel.NavMenu> list = new ArrayList<>();
        //menu home
        NavigationMenuViewModel.NavMenu menu = new NavigationMenuViewModel.NavMenu();
        menu.ItemDrawable.setValue(R.drawable.ic_home_orange);
        menu.ItemName.setValue(getResString(R.string.lbl_home));
        list.add(menu);
        //menu my delivery's
        menu = new NavigationMenuViewModel.NavMenu();
        menu.ItemDrawable.setValue(R.drawable.ic_my_delivery);
        menu.ItemName.setValue(getResString(R.string.lbl_my_deliverys));
        list.add(menu);
        //payment
        menu = new NavigationMenuViewModel.NavMenu();
        menu.ItemDrawable.setValue(R.drawable.ic_credit_card);
        menu.ItemName.setValue(getResString(R.string.lbl_payment));
        list.add(menu);
        //setting
        menu = new NavigationMenuViewModel.NavMenu();
        menu.ItemDrawable.setValue(R.drawable.ic_setting_orange);
        menu.ItemName.setValue(getResString(R.string.lbl_settings));
        list.add(menu);
        //logout
        menu = new NavigationMenuViewModel.NavMenu();
        menu.ItemDrawable.setValue(R.drawable.ic_logout);
        menu.ItemName.setValue(getResString(R.string.lbl_logout));
        list.add(menu);
        return list;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivNav:
                navigationOpen(true);
                break;
            case R.id.ivNavClose:
                navigationOpen(false);
                break;
            case R.id.clMenuItem:
                navItemPos = Integer.parseInt(String.valueOf(view.getTag()));
                switch (navItemPos) {
                    case 0:
                        navigationOpen(false);
                        break;
                    case 1:

                        break;

                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:
                        callApi(1);
                        break;
                }
                break;
        }
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
        if (isInternet) {
            if (tag == 1) {
                enableScreen(false);
                showProgressBar(true);
                apiController.getApiLogout();
                apiController.set0status(false);
            }
        } else {
            utils.showInternetMsg(mContext);
        }
    }

    @Override
    protected void enableScreen(boolean enable) {
        binding.nonClickable.setVisibility(enable ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onResponse(String tag, ApiConst.API_RESULT result, int status, String msg) {
        super.onResponse(tag, result, status, msg);
        if (tag == ApiConst.LOGOUT && result == ApiConst.API_RESULT.SUCCESS && status == 1) {
            utils.logoutFromApp(mContext);
        } else if (tag == ApiConst.LOGOUT && result == ApiConst.API_RESULT.FAIL) {
            enableScreen(true);
            showProgressBar(false);
        }
    }

    @Override
    public void onNewDataMessage(String from, String msg, Data data) {

    }

    @Override
    public void onNegativeClicked(String type) {

    }

    @Override
    public void onPositiveClicked(String type) {

    }

    @Override
    public void onChanged(DashBoardBean dashBoardBean) {
        Utils.print(TAG, "Profile Name = " + dashBoardBean.getProfileName());
        Utils.print(TAG, "Profile Email = " + dashBoardBean.getProfileEmail());
    }

    private void navigationOpen(boolean open) {
        if (open) {
            mapBinding.ivNav.setEnabled(false);
            binding.clMap.setVisibility(View.GONE);
            binding.clNavigation.setVisibility(View.VISIBLE);
            scaleAnimation = utils.getScaleAnimation(1f, 0f, 1f, 1f, 0f, 0f, 250);
            scaleAnimation.setDuration(animDuration);
            binding.clNavigation.startAnimation(scaleAnimation);
        } else {
            scaleAnimation = utils.getScaleAnimation(1f, 1f, 1f, 0f, 0f, 0f, 250);
            scaleAnimation.setDuration(animDuration);
            binding.clNavigation.startAnimation(scaleAnimation);
        }
        handler.postDelayed(() -> {
            if (open) {
                binding.clNavigation.clearAnimation();
                mapBinding.ivNav.setEnabled(true);
            } else {
                binding.clNavigation.clearAnimation();
                binding.clNavigation.setVisibility(View.GONE);
                binding.clMap.setVisibility(View.VISIBLE);
            }
        }, animDuration - 50);
    }
}
