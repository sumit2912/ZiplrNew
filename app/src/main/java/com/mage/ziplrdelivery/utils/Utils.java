package com.mage.ziplrdelivery.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mage.ziplrdelivery.MyApplication;
import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.common.AppManager;
import com.mage.ziplrdelivery.model.data.Result;
import com.mage.ziplrdelivery.prefmanager.PrefConst;
import com.mage.ziplrdelivery.prefmanager.PrefManager;
import com.mage.ziplrdelivery.ui.LoginMainActivity;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


public class Utils {

    public static boolean DO_SOP = true;

    public static void print(String mesg) {
        if (Utils.DO_SOP) {
            System.out.println(mesg);
        }
    }

    public static void print(String title, String mesg) {
        if (Utils.DO_SOP) {
            Utils.print(title + " :: " + mesg);
        }
    }

    public static void print(String title, Exception e) {
        if (Utils.DO_SOP) {
            System.out.println("=======================" + title + "===========================");
            e.printStackTrace();
        }
    }

    public static void toast(Context context, String mgs, boolean isShort) {
        View layoutValue = LayoutInflater.from(context).inflate(R.layout.toast, null);
        Toast toast = new Toast(context);
        toast.setDuration(isShort ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG);
        TextView text = layoutValue.findViewById(R.id.text);
        text.setText(mgs);
        toast.setView(layoutValue);//setting the view of custom toast layout
        toast.show();
    }

    public static void showInternetMsg(Context context) {
        toast(context, context.getResources().getString(R.string.no_internet), false);
    }

    public static String padInt(int i) {
        if (i < 10) {
            return "0" + i;
        } else {
            return "" + i;
        }
    }

    public static void hideKeyBoardFromView(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        // Find the currently focused view, so we can grab the correct window
        // token from it.
        View view = ((Activity) context).getCurrentFocus();
        // If no view currently has focus, create a new one, just so we can grab
        // a window token from it
        if (view == null) {
            view = new View(context);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showSessionDialog(final Context context) {
        Utils.toast(context, context.getResources().getString(R.string.session_expired_please_login_again), false);
        MyApplication.getAppManager().getPrefManager().clearAll();
        Intent i = new Intent(context, LoginMainActivity.class);
        ((Activity) context).finishAffinity();
        context.startActivity(i);
    }

    public static char getChar(int length) {
        return String.valueOf(length).charAt(0);
    }

    public static void makeFullScreenActivity(AppCompatActivity activity) {
        Window window = activity.getWindow();
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static float getFloatFromDimen(Context context, int dimen) {
        TypedValue outValue = new TypedValue();
        context.getResources().getValue(dimen, outValue, true);
        return outValue.getFloat();
    }

    public static void storeLoginData(AppManager appManager, Result data) {
        PrefManager pm = appManager.getPrefManager();
        pm.setLong(PrefConst.PREF_USER_ID, data.getId());
        pm.setString(PrefConst.PREF_USER_NAME, data.getName());
        pm.setString(PrefConst.PREF_USER_EMAIL, data.getEmail());
        pm.setString(PrefConst.PREF_USER_AVATAR_URL, data.getAvatarUrl());
        pm.setString(PrefConst.PREF_USER_PHONE_NUMBER, data.getPhoneNumber());
        pm.setString(PrefConst.PREF_USER_COUNTRY_CODE, data.getCountryCode());
    }

    public static Result getLoginData(AppManager appManager) {
        PrefManager pm = appManager.getPrefManager();
        Result result = new Result();
        result.setId(pm.getLong(PrefConst.PREF_USER_ID));
        result.setName(pm.getString(PrefConst.PREF_USER_NAME));
        result.setEmail(pm.getString(PrefConst.PREF_USER_EMAIL));
        result.setAvatarUrl(pm.getString(PrefConst.PREF_USER_AVATAR_URL));
        result.setPhoneNumber(pm.getString(PrefConst.PREF_USER_PHONE_NUMBER));
        result.setCountryCode(pm.getString(PrefConst.PREF_USER_COUNTRY_CODE));
        return result;
    }

    public static void logoutFromApp(Context mContext) {
        PrefManager prefManager = (mContext == null) ? MyApplication.getAppManager().getPrefManager() : new AppManager(mContext).getPrefManager();
        Utils.print("Clearing Token = " + prefManager.getString(PrefConst.PREF_ACCESS_TOKEN));
        prefManager.clearAll();
        mContext.startActivity(new Intent(mContext, LoginMainActivity.class));
        ((Activity) mContext).finishAffinity();
    }

    public static class InternetCheck extends AsyncTask<Void, Void, Boolean> {

        private NetListener netListener;

        public InternetCheck(NetListener netListener) {
            this.netListener = netListener;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Socket sock = new Socket();
                sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
                sock.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean internet) {
            print("Utils", "internet = " + internet);
            netListener.onNetChange(internet);
        }

        public interface NetListener {
            void onNetChange(boolean isInternet);
        }
    }
}
