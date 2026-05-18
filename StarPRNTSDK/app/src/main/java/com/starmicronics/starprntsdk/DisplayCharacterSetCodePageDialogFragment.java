package com.starmicronics.starprntsdk;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class DisplayCharacterSetCodePageDialogFragment extends CommonAlertDialogFragment {

    private Callback mCallbackTarget;

    public static DisplayCharacterSetCodePageDialogFragment newInstance(String tag) {
        DisplayCharacterSetCodePageDialogFragment dialogFragment = new DisplayCharacterSetCodePageDialogFragment();

        Bundle args = new Bundle();
        args.putString(DIALOG_TAG, tag);
        args.putBoolean(CANCEL, false);
        args.putBoolean(CALLBACK, true);
        args.putString(LABEL_NEGATIVE, "Cancel");

        dialogFragment.setArguments(args);
        dialogFragment.setCancelable(false);

        return dialogFragment;
    }

    @Override
    public @NonNull
    Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select Character Set (Code Page).");

        builder.setItems(new String[]{
                        "Code Page 437",
                        "Katakana",
                        "Code Page 850",
                        "Code Page 860",
                        "Code Page 863",
                        "Code Page 865",
                        "Code Page 1252",
                        "Code Page 866",
                        "Code Page 852",
                        "Code Page 858",
                        "Japanese",
                        "Simplified Chinese",
                        "Traditional Chinese",
                        "Hangul"},
                mOnCodePageClickListener);

        setupNegativeButton(builder);

        mCallbackTarget = (Callback) getParentFragment();

        return builder.create();
    }

    private DialogInterface.OnClickListener mOnCodePageClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent intentForPassingData = new Intent();
            intentForPassingData.putExtra(CommonActivity.BUNDLE_KEY_INDEX, which);

            mCallbackTarget.onDialogResult(getArguments().getString(DIALOG_TAG), intentForPassingData);

            dismiss();
        }
    };
}
