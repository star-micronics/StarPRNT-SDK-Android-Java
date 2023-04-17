package com.starmicronics.starprntsdk;

import android.widget.CompoundButton;

class TextInfo {
    private static final int defaultTextColor = 0xFF4285F4;

    private final String  mText;
    private final int     mTextResourceID;
    private final int     mAnimationResourceID;
    private final int     mTextColor;
    private       boolean mIsAnimated;
    private       boolean mIsCustomTextColor;

    TextInfo(String text, int resourceID) {
        this(text, resourceID, defaultTextColor);
        mIsAnimated        = false;
        mIsCustomTextColor = false;
    }

    TextInfo(String text, int resourceID, int textColor) {
        this(text, resourceID, 0, textColor);
        mIsAnimated        = false;
        mIsCustomTextColor = true;
    }

    TextInfo(String text, int textResourceId, int animationResourceId, int textColor) {
        mText                = text;
        mTextResourceID      = textResourceId;
        mAnimationResourceID = animationResourceId;
        mTextColor           = textColor;
        mIsAnimated          = true;
        mIsCustomTextColor   = true;
    }

    String getText() {
        return mText;
    }

    int getTextResourceID() {
        return mTextResourceID;
    }

    int getAnimationResourceID() {
        return mAnimationResourceID;
    }

    int getTextColor() { return mTextColor; }

    boolean isAnimated() {
        return mIsAnimated;
    }

    boolean isCustomTextColor() {
        return mIsCustomTextColor;
    }
}

class SwitchInfo {
    private boolean mIsChecked;
    private int     mResourceID;
    private String  mTag;
    private CompoundButton.OnCheckedChangeListener mListener;

    SwitchInfo(boolean isChecked, int resourceID, String tag, CompoundButton.OnCheckedChangeListener listener) {
        mIsChecked  = isChecked;
        mResourceID = resourceID;
        mTag        = tag;
        mListener   = listener;
    }

    boolean isChecked() {
        return mIsChecked;
    }

    int getResourceID() {
        return mResourceID;
    }

    String getTag() {
        return mTag;
    }

    CompoundButton.OnCheckedChangeListener getListener() {
        return mListener;
    }
}

class ImgInfo {
    private int mImgResourceID;
    private int mResourceID;

    ImgInfo(int imgResourceID, int resourceID) {
        mImgResourceID = imgResourceID;
        mResourceID    = resourceID;
    }

    int getImgResourceID() {
        return mImgResourceID;
    }

    int getResourceID() {
        return mResourceID;
    }
}

