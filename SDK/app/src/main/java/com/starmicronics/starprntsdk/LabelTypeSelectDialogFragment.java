package com.starmicronics.starprntsdk;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class LabelTypeSelectDialogFragment extends CommonAlertDialogFragment {

    private Callback mCallbackTarget;

    public static LabelTypeSelectDialogFragment newInstance(String tag) {
        LabelTypeSelectDialogFragment dialogFragment = new LabelTypeSelectDialogFragment();

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

        builder.setTitle("Select label type.");

        builder.setItems(new String[]{
                        "Invalid",
                        "Valid",
                        "Valid with Detection"},
                mOnTypeClickListener);

        setupNegativeButton(builder);

        mCallbackTarget = (Callback) getParentFragment();

        return builder.create();
    }

    private DialogInterface.OnClickListener mOnTypeClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent intentForPassingData = new Intent();
            intentForPassingData.putExtra(CommonActivity.BUNDLE_KEY_LABEL_TYPE_INDEX, which);

            mCallbackTarget.onDialogResult(getArguments().getString(DIALOG_TAG), intentForPassingData);

            dismiss();
        }
    };
}
