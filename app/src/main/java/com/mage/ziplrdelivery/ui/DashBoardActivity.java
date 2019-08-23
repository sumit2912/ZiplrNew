package com.mage.ziplrdelivery.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.ScaleAnimation;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.material.card.MaterialCardView;
import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.adapter.NavigationMenuAdapter;
import com.mage.ziplrdelivery.adapter.ParcelAdapter;
import com.mage.ziplrdelivery.databinding.LayoutMapViewBinding;
import com.mage.ziplrdelivery.databinding.LayoutNavigationViewBinding;
import com.mage.ziplrdelivery.model.data.DashBoardBean;
import com.mage.ziplrdelivery.model.param.ParcelBean;
import com.mage.ziplrdelivery.screen.Data;
import com.mage.ziplrdelivery.screen.Screen;
import com.mage.ziplrdelivery.databinding.ActivityDashBoardBinding;
import com.mage.ziplrdelivery.screen.ScreenHelper;
import com.mage.ziplrdelivery.api.ApiConst;
import com.mage.ziplrdelivery.uc.CustomAutoCT;
import com.mage.ziplrdelivery.uc.CustomTextWatcher;
import com.mage.ziplrdelivery.uc.ProgressMaterialButton;
import com.mage.ziplrdelivery.uc.SliderLayoutManager;
import com.mage.ziplrdelivery.utils.Utils;
import com.mage.ziplrdelivery.viewmodelfactory.viewmodel.DashBoardViewModel;
import com.mage.ziplrdelivery.viewmodelfactory.viewmodel.NavigationMenuViewModel;

import java.util.ArrayList;
import java.util.List;

public class DashBoardActivity extends BaseActivity implements ScreenHelper.DataMessageListener, Observer<DashBoardBean>, OnMapReadyCallback, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraIdleListener, ParcelAdapter.ParcelClickListener, SliderLayoutManager.OnItemSelectedListener, CustomTextWatcher.TextWatcherListener {

    private static final String TAG = Screen.DASH_BOARD_ACTIVITY;
    private static final int ACT_FROM_TAG = 0;
    private static final int ACT_TO_TAG = 1;
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
    private Intent settingsIntent;
    private GoogleMap mMap;
    private ConstraintLayout clLocation, clParcel;
    private MaterialCardView mcvLocToFrom, mcvCurLocation;
    private CustomAutoCT actFrom, actTo;
    private SliderLayoutManager llManager2;
    private ParcelAdapter parcelAdapter;
    private RecyclerView rvParcel;
    private ProgressMaterialButton btSubmitParcel;
    List<ParcelBean> parcelList;
    private String actFromText="",actToText="";
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
        //MapView
        initMapView();
        //Navigation Menu
        initNavigationView();
    }

    private void initMapView() {
        clLocation = mapBinding.clLocation;
        clParcel = mapBinding.clParcel;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mcvCurLocation = mapBinding.mcvLocToFrom;
        mcvCurLocation = mapBinding.mcvCurLocation;
        actFrom = mapBinding.actFrom;
        actTo = mapBinding.actTo;
        actFrom.addTextChangedListener(new CustomTextWatcher(ACT_FROM_TAG,this));
        actTo.addTextChangedListener(new CustomTextWatcher(ACT_TO_TAG,this));
        rvParcel = mapBinding.rvParcel;
        llManager2 = new SliderLayoutManager(mContext, rvParcel, this);
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(rvParcel);
        rvParcel.setLayoutManager(llManager2);
        parcelList = getParcelList();
        parcelAdapter = new ParcelAdapter(parcelList, this);
        rvParcel.setAdapter(parcelAdapter);
        btSubmitParcel = mapBinding.btSubmitParcel;
    }

    private List<ParcelBean> getParcelList() {
        List<ParcelBean> list = new ArrayList<>();
        ParcelBean bean = new ParcelBean();
        bean.setIndex(0);
        bean.setParcelType("Small");
        bean.setDrawable(R.drawable.icn_small);
        bean.setParcelQuantity(1);
        bean.setParcelWeightRange("1 - 10kg");
        bean.setParcelPriceRange("£1 - £10");
        list.add(bean);

        bean = new ParcelBean();
        bean.setIndex(1);
        bean.setParcelType("Medium");
        bean.setDrawable(R.drawable.icn_medium);
        bean.setParcelQuantity(0);
        bean.setParcelWeightRange("10 - 20kg");
        bean.setParcelPriceRange("£8 - £15");
        list.add(bean);

        bean = new ParcelBean();
        bean.setIndex(2);
        bean.setParcelType("Large");
        bean.setDrawable(R.drawable.icn_large);
        bean.setParcelQuantity(5);
        bean.setParcelWeightRange("20 - 30kg");
        bean.setParcelPriceRange("£16 - £22");
        list.add(bean);
        return list;
    }

    private void initNavigationView() {
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
                        navigationOpen(false);
                        handler.postDelayed(() -> {
                            if (!disableClick) {
                                disableClick = true;
                                if (settingsIntent == null)
                                    settingsIntent = new Intent(DashBoardActivity.this, SettingsActivity.class);
                                startActivity(settingsIntent);
                            }
                        }, animDuration);
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
            scaleAnimation = utils.getScaleAnimation(1f, 0f, 1f, 1f, 0f, 0f, animDuration);
            scaleAnimation.setDuration(animDuration);
            binding.clNavigation.startAnimation(scaleAnimation);
        } else {
            scaleAnimation = utils.getScaleAnimation(1f, 1f, 1f, 0f, 0f, 0f, animDuration);
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

    @Override
    public void onBackPressed() {
        if (binding.clNavigation.getVisibility() == View.VISIBLE) {
            navigationOpen(false);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
            if (!success) {
                Log.i("Map:Style", "Style parsing failed.");
            } else {
                Log.i("Map:Style", "Style Applied.");
            }
        } catch (Resources.NotFoundException e) {
            Log.i("Map:Style", "Can't find style. Error: ");
        }

        mMap = googleMap;

        if (mMap != null) {
            mMap.getUiSettings().setCompassEnabled(false);
            mMap.setBuildingsEnabled(true);
            mMap.setOnCameraMoveListener(this);
            mMap.setOnCameraIdleListener(this);
            mMap.getUiSettings().setRotateGesturesEnabled(false);
            mMap.getUiSettings().setTiltGesturesEnabled(false);
        }
    }

    @Override
    public void onCameraMove() {

    }

    @Override
    public void onCameraIdle() {

    }

    @Override
    public void onParcelClicked(int pos, View view) {
        Utils.print(TAG,"Pos = "+pos);
        int q = parcelAdapter.getParcelList().get(pos).getParcelQuantity();
        switch (view.getId()){
            case R.id.ivMinus:
                if(q>0) {
                    parcelAdapter.getParcelList().get(pos).setParcelQuantity(q-1);
                    //parcelAdapter.notifyItemChanged(pos);
                }
                Utils.print(TAG,"ivMinus = "+getParcelList().get(pos).getParcelQuantity());
                break;
            case R.id.ivPlus:
                parcelAdapter.getParcelList().get(pos).setParcelQuantity(q+1);
                //parcelAdapter.notifyItemChanged(pos);
                Utils.print(TAG, "ivPlus = " + getParcelList().get(pos).getParcelQuantity());
                break;
        }
    }

    @Override
    public void onItemSelected(int selectedPos) {
        String text = parcelList.get(selectedPos).getParcelPriceRange();
        Utils.print(TAG,"text = "+text);
        btSubmitParcel.setButtonText(text);
    }

    @Override
    public void onTextChanged(int edTag, String text) {
        switch (edTag){
            case ACT_FROM_TAG:
                actFromText = text;
                break;
            case ACT_TO_TAG:
                actToText = text;
                break;
        }

        if(!actFromText.isEmpty() && !actToText.isEmpty() && actFromText.length() >4 && actToText.length()>4){
            goToParcelScreen();
        }
    }

    private void goToParcelScreen() {
        ScaleAnimation scaleAnimation = utils.getScaleAnimation(1f,1f,1f,0f,1f,1f,animDuration);
        scaleAnimation.setDuration(animDuration);
        clParcel.startAnimation(scaleAnimation);
        utils.hideKeyBoardFromView(mContext);
        handler.postDelayed(() -> {
            clLocation.setVisibility(View.GONE);
            clLocation.clearAnimation();
            clParcel.setVisibility(View.VISIBLE);
            btSubmitParcel.setButtonText(parcelList.get(0).getParcelPriceRange());
            ScaleAnimation scaleAnim = utils.getScaleAnimation(1f,0f,1f,1f,1f,1f,animDuration);
            scaleAnim.setDuration(animDuration);
            clParcel.startAnimation(scaleAnim);
            handler.postDelayed(() -> {
                clParcel.clearAnimation();
            }, animDuration - 10);
        }, animDuration - 10);
    }
}
