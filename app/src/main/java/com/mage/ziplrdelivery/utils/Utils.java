package com.mage.ziplrdelivery.utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mage.ziplrdelivery.R;

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

    public static void showInternetMsg(Context context){
        toast(context,context.getResources().getString(R.string.no_internet),false);
    }

    public static void logout(Context caller){
    }
    public static String padLong(long l){
        if (l<10){
            return "0"+l;
        }else {
            return ""+l;
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

    /*public static void showSessionDialog(final Context context) {
        Utils.toast(context,context.getResources().getString(R.string.session_expired_please_login_again),false);
        Utils.logout(context);
        Intent i = new Intent(context, SignInActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
        ((Activity) context).finish();
    }*/

    public static char getChar(int length)
    {
        return String.valueOf(length).charAt(0);
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
            print("Utils","internet = " + internet);
            netListener.onNetChange(internet);
        }

        public interface NetListener{
            void onNetChange(boolean isInternet);
        }
    }

    public static void makeFullScreenActivity(AppCompatActivity activity){
        Window window = activity.getWindow();
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
