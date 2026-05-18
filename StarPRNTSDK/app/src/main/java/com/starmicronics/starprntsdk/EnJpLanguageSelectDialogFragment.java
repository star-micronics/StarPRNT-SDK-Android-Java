package com.starmicronics.starprntsdk;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class EnJpLanguageSelectDialogFragment extends CommonAlertDialogFragment {
    
    private Callback mCallbackTarget;

    public static EnJpLanguageSelectDialogFragment newInstance(String tag) {
        EnJpLanguageSelectDialogFragment dialogFragment = new EnJpLanguageSelectDialogFragment();

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

        builder.setTitle("Select language.");

        builder.setItems(new String[]{
                        "English",
                        "Japanese"},
                        mOnLanguageClickListener);

        setupNegativeButton(builder);

        mCallbackTarget = (Callback) getParentFragment();

        return builder.create();
    }

    private DialogInterface.OnClickListener mOnLanguageClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            int selectedLanguage = PrinterSettingConstant.LANGUAGE_ENGLISH;

            switch (which) {
                case 0: selectedLanguage = PrinterSettingConstant.LANGUAGE_ENGLISH; break;
                case 1: selectedLanguage = PrinterSettingConstant.LANGUAGE_JAPANESE; break;
            }

            Intent intentForPassingData = new Intent();
            intentForPassingData.putExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, selectedLanguage);

            mCallbackTarget.onDialogResult(getArguments().getString(DIALOG_TAG), intentForPassingData);

            dismiss();
        }
    };
}
