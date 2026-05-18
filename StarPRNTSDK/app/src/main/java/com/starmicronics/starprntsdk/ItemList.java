package com.starmicronics.starprntsdk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

class ItemListAdapter extends ArrayAdapter<ItemList> {
    final private LayoutInflater mInflater;
    final private List<ItemList> mItems;

    ItemListAdapter(Context context, List<ItemList> items) {
        super(context, 0, items);

        mItems    = items;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @SuppressLint("ViewHolder") // for easy to understand
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ItemList item = mItems.get(position);

        convertView = mInflater.inflate(item.getLayoutResourceID(), parent, false);

        if (item.isCustomBackGroundColor()) {
            convertView.setBackgroundColor(item.getBackGroundColor());
        }

        List<TextInfo> textList = item.getTextList();

        if (textList != null) {
            for (TextInfo textInfo : textList) {
                TextView textView = convertView.findViewById(textInfo.getTextResourceID());
                textView.setText(textInfo.getText());

                if (textInfo.isCustomTextColor()) {
                    textView.setTextColor(textInfo.getTextColor());
                }

                if (textInfo.isAnimated()) {
                    Animation animation = AnimationUtils.loadAnimation(getContext(), textInfo.getAnimationResourceID());
                    textView.startAnimation(animation);
                }
            }
        }

        List<SwitchInfo> switchList = item.getSwitchList();

        if (switchList != null) {
            for (SwitchInfo switchInfo : switchList) {
                Switch switchView = convertView.findViewById(switchInfo.getResourceID());
                switchView.setChecked(switchInfo.isChecked());
                switchView.setTag(switchInfo.getTag());
                switchView.setOnCheckedChangeListener(switchInfo.getListener());
            }
        }

        List<ImgInfo> imgList = item.getImgList();

        if (imgList != null) {
            for (ImgInfo imgInfo : imgList) {
                ImageView imageView = convertView.findViewById(imgInfo.getResourceID());
                imageView.setImageResource(imgInfo.getImgResourceID());
            }
        }

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        return mItems.get(position).isClickable();      // True: can click list.  False: cannot click list.
    }
}

class ItemList {
    private final int                 mLayoutResourceID;
    private final List<TextInfo>      mTextList;
    private final List<SwitchInfo>    mSwitchList;
    private       List<ImgInfo>       mImgList;
    private final int                 mBackGroundColor;
    private final boolean             mIsCustomBackGroundColor;
    private final boolean             mIsClickable;

    ItemList(int layoutResourceID, List<TextInfo> textList, boolean isClickable) {
        this.mLayoutResourceID        = layoutResourceID;
        this.mTextList                = textList;
        this.mSwitchList              = null;
        this.mImgList                 = null;
        this.mBackGroundColor         = Color.TRANSPARENT;
        this.mIsCustomBackGroundColor = false;
        this.mIsClickable             = isClickable;
    }

    ItemList(int layoutResourceID, List<TextInfo> textList, int backGroundColor, boolean isClickable) {
        this.mLayoutResourceID        = layoutResourceID;
        this.mTextList                = textList;
        this.mSwitchList              = null;
        this.mImgList                 = null;
        this.mBackGroundColor         = backGroundColor;
        this.mIsCustomBackGroundColor = true;
        this.mIsClickable             = isClickable;
    }

    ItemList(int layoutResourceID, List<TextInfo> textList, List<SwitchInfo> switchList, boolean isClickable) {
        this.mLayoutResourceID        = layoutResourceID;
        this.mTextList                = textList;
        this.mSwitchList              = switchList;
        this.mImgList                 = null;
        this.mBackGroundColor         = Color.TRANSPARENT;
        this.mIsCustomBackGroundColor = false;
        this.mIsClickable             = isClickable;
    }

    ItemList(int layoutResourceID, List<TextInfo> textList, List<ImgInfo> imgList) {
        this.mLayoutResourceID        = layoutResourceID;
        this.mTextList                = textList;
        this.mSwitchList              = null;
        this.mImgList                 = imgList;
        this.mBackGroundColor         = Color.TRANSPARENT;
        this.mIsCustomBackGroundColor = false;
        this.mIsClickable             = true;
    }

    int getLayoutResourceID() {
        return mLayoutResourceID;
    }

    List<TextInfo> getTextList() {
        return mTextList;
    }

    List<SwitchInfo> getSwitchList() {
        return mSwitchList;
    }

    List<ImgInfo> getImgList() {
        return mImgList;
    }

    void setImgList(List<ImgInfo> imgList) {
        mImgList = imgList;
    }

    int getBackGroundColor() {
        return mBackGroundColor;
    }

    boolean isCustomBackGroundColor() {
        return mIsCustomBackGroundColor;
    }

    boolean isClickable() {
        return mIsClickable;
    }
}
