package com.mage.ziplrdelivery.uc;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.mage.ziplrdelivery.R;

public class CustomMaterialButton extends MaterialButton {

    private static final int BOLD = 1;
    private static final int SEMI_BOLD = 2;
    private static final int REGULAR = 3;
    private int fontTag = 0;

    public CustomMaterialButton(Context context) {
        super(context);
        init(context, null);
    }

    public CustomMaterialButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomMaterialButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomView);
            fontTag = attributes.getInteger(R.styleable.CustomView_custom_font, fontTag);
            setFont(context, fontTag);
            attributes.recycle();
        }
    }

    private void setFont(Context context, int tag) {
        Typeface typeface = null;
        switch (tag) {
            case BOLD:
                typeface = Typeface.createFromAsset(context.getAssets(), "font/ProximaNova-Bold.ttf");
                break;
            case SEMI_BOLD:
                typeface = Typeface.createFromAsset(context.getAssets(), "font/ProximaNova-Semibold.ttf");
                break;
            case REGULAR:
                typeface = Typeface.createFromAsset(context.getAssets(), "font/ProximaNova-Regular.ttf");
                break;
        }
        this.setTypeface(typeface);
    }
}
