package com.mage.ziplrdelivery.common;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.mage.ziplrdelivery.R;

public class AlertDialogManager {
    private Context context;
    private String type;
    private String title;
    private String message;
    private String negative;
    private String positive;
    private AlertDialogListener listener;
    private AlertDialog dialog;

    public AlertDialogManager(Context context, String negative, String positive, AlertDialogListener listener) {
        this.context = context;
        this.negative = negative;
        this.positive = positive;
        this.listener = listener;
    }

    public void init(String type, String title, String message) {
        this.type = type;
        this.title = title;
        this.message = message;
    }

    public void setNegativePositive(String negative, String positive) {
        this.negative = negative;
        this.positive = positive;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        if (title != null) {
            builder.setTitle(title);
        }
        if (message != null) {
            builder.setMessage(message);
        }

        if (negative != null) {
            builder.setNegativeButton(negative.isEmpty() ? context.getResources().getString(R.string.lbl_cancel) : negative, (dialog, i) -> {
                dialog.dismiss();
                listener.onNegativeClicked(type);
            });
        }

        if (positive != null) {
            builder.setPositiveButton(positive.isEmpty() ? context.getResources().getString(R.string.lbl_ok) : positive, (dialog, i) -> {
                dialog.dismiss();
                listener.onPositiveClicked(type);
            });
        }

        dialog = builder.create();

        if (!dialog.isShowing())
            dialog.show();
    }

    public interface AlertDialogListener {
        void onNegativeClicked(String type);

        void onPositiveClicked(String type);
    }
}
