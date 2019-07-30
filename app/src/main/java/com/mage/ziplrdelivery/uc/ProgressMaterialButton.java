package com.mage.ziplrdelivery.uc;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.mage.ziplrdelivery.R;
import com.mage.ziplrdelivery.listener.ProgressListener;

public class ProgressMaterialButton extends RelativeLayout {

    public MaterialButton materialButton;
    public ProgressBar progressBar;
    private Context context;
    private String text = "";
    private int buttonWidth = -1, buttonHeight = -1;
    private int cornerRadius = 0;
    private ValueAnimator widthAnimator = null;
    private OnClickListener onClickListener;
    private LayoutParams paramsButton, paramsProgress;
    private float buttonElevation = 0;
    private long animationDuration = 0;
    private int textColor, backgroundColor, pbColor;
    private ProgressListener progressListener;
    private String fontPath = "";
    private int textSize = 0;

    public ProgressMaterialButton(Context context) {
        super(context);
        init(context, null);
    }

    public ProgressMaterialButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProgressMaterialButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        paramsButton = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        materialButton = new MaterialButton(context);
        materialButton.setFocusable(false);
        materialButton.setFocusableInTouchMode(false);
        materialButton.setClickable(false);
        paramsButton.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        materialButton.setLayoutParams(paramsButton);
        materialButton.setElevation(buttonElevation);
        paramsProgress = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        progressBar = new ProgressBar(context);
        progressBar.setFocusable(false);
        progressBar.setFocusableInTouchMode(false);
        progressBar.setClickable(false);
        paramsProgress.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        progressBar.setLayoutParams(paramsProgress);
        progressBar.setVisibility(GONE);
        progressBar.setIndeterminate(true);
        addView(materialButton, 0);
        addView(progressBar, 1);
        if (attrs != null) {
            final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomMaterialButton);

            if (attributes.hasValue(R.styleable.CustomMaterialButton_bt_text))
                text = attributes.getString(R.styleable.CustomMaterialButton_bt_text);
            setButtonText(text);

            textColor = attributes.getResourceId(R.styleable.CustomMaterialButton_bt_text_color, -1);
            setButtonTextColor(textColor);

            textSize = attributes.getResourceId(R.styleable.CustomMaterialButton_bt_text_size,textSize);
            materialButton.setTextSize(TypedValue.COMPLEX_UNIT_PX,context.getResources().getDimensionPixelSize(textSize));

            materialButton.setAllCaps(attributes.getBoolean(R.styleable.CustomMaterialButton_bt_text_all_caps,false));

            backgroundColor = attributes.getResourceId(R.styleable.CustomMaterialButton_bt_background_color, -1);
            setButtonBackgroundColor(backgroundColor);

            pbColor = attributes.getResourceId(R.styleable.CustomMaterialButton_pb_color, -1);
            setProgressBarColor(pbColor);

            cornerRadius = attributes.getDimensionPixelSize(R.styleable.CustomMaterialButton_bt_corner_radius, 0);
            setButtonCornerRadius(cornerRadius);

            buttonElevation = attributes.getDimension(R.styleable.CustomMaterialButton_bt_elevation, 0);
            setButtonElevation(buttonElevation);

            if (attributes.hasValue(R.styleable.CustomMaterialButton_bt_font_path)) {
                fontPath = attributes.getString(R.styleable.CustomMaterialButton_bt_font_path);
            }
            if (!TextUtils.isEmpty(fontPath)) {
                setButtonFontPath(fontPath);
            }

            animationDuration = attributes.getInteger(R.styleable.CustomMaterialButton_bt_anim_duration, 250);
            setButtonAnimationDuration(animationDuration);
            attributes.recycle();
        }
    }

    public void setButtonText(String text) {
        this.text = text;
        materialButton.setText(this.text);
    }

    public void setButtonText(int resId) {
        this.text = context.getResources().getString(resId);
        materialButton.setText(this.text);
    }

    public String getButtonText() {
        return this.text;
    }

    public void setButtonTextColor(int resId) {
        textColor = resId;
        try {
            materialButton.setTextColor(context.getResources().getColor(textColor));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setButtonElevation(float elevation) {
        this.buttonElevation = elevation;
        materialButton.setElevation(elevation);
    }

    public float getButtonElevation() {
        return buttonElevation;
    }

    public void setButtonCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        materialButton.setCornerRadius(this.cornerRadius);
    }

    public int getButtonCornerRadius() {
        return this.cornerRadius;
    }

    public void setButtonBackgroundColor(int resId) {
        backgroundColor = resId;
        try {
            materialButton.setBackgroundTintList(ContextCompat.getColorStateList(context, backgroundColor));
        } catch (Exception e) {
        }
    }

    public void setButtonFontPath(String fontPath) {
        this.fontPath = fontPath;
        try {
            materialButton.setTypeface(Typeface.createFromAsset(context.getAssets(), fontPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getButtonAnimationDuration() {
        return animationDuration;
    }

    public void setButtonAnimationDuration(long animationDuration) {
        this.animationDuration = animationDuration;
    }

    public void setProgressBarColor(int resId) {
        pbColor = resId;
        try {
            progressBar.getIndeterminateDrawable().setTint(context.getResources().getColor(pbColor));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setButtonClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        setOnClickListener(this.onClickListener);
    }

    public void setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }

    public void showProgressBar(boolean show, int tag) {
        if (buttonWidth == -1 && buttonHeight == -1) {
            buttonWidth = getWidth();
            buttonHeight = getHeight();
        }
        if (show) {
            widthAnimator = ValueAnimator.ofInt(buttonWidth, buttonHeight);
            widthAnimator.setDuration(animationDuration);
            materialButton.setVisibility(GONE);
            setBackground(getGradientDrawable(materialButton.getBackgroundTintList().getColorForState(MaterialButton.PRESSED_ENABLED_STATE_SET, 0)));
            this.postDelayed(() -> {
                progressBar.setVisibility(VISIBLE);
            }, animationDuration);
            setOnClickListener(null);
        } else {
            widthAnimator = ValueAnimator.ofInt(buttonHeight, buttonWidth);
            widthAnimator.setDuration(animationDuration);
            this.postDelayed(() -> {
                setOnClickListener(onClickListener);
                materialButton.setText(text);
                setBackground(null);
                materialButton.setVisibility(VISIBLE);
                progressBar.setVisibility(GONE);
            }, animationDuration);
        }
        if (widthAnimator != null) {
            widthAnimator.addUpdateListener(value -> {
                getLayoutParams().width = (int) value.getAnimatedValue();
                requestLayout();
            });
            widthAnimator.start();
        }
        if (progressListener != null)
            progressListener.onProgressChange(show, tag);
    }

    public GradientDrawable getGradientDrawable(int color) {
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setCornerRadius(buttonHeight);
        gd.setColor(color);
        return gd;
    }
}
