package com.starmicronics.starprntsdk;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class DrawerOpenActiveSelectDialogFragment extends CommonAlertDialogFragment {
    private CommonAlertDialogFragment.Callback mCallbackTarget;

    public static DrawerOpenActiveSelectDialogFragment newInstance(String tag) {
        DrawerOpenActiveSelectDialogFragment dialogFragment = new DrawerOpenActiveSelectDialogFragment();

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

        builder.setTitle("Select CashDrawer Open Status.");

        builder.setItems(new String[]{
                        "High when Open",
                        "Low when Open"},
                mOnDrawerStatusClickListener);

        setupNegativeButton(builder);

        mCallbackTarget = (CommonAlertDialogFragment.Callback) getParentFragment();

        return builder.create();
    }

    private DialogInterface.OnClickListener mOnDrawerStatusClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            boolean activeHigh;

            switch (which) {
                default:
                case  0: activeHigh = true; break;
                case  1: activeHigh = false; break;
            }

            Intent intentForPassingData = new Intent();
            intentForPassingData.putExtra(CommonActivity.BUNDLE_KEY_DRAWER_OPEN_STATUS, activeHigh);

            mCallbackTarget.onDialogResult(getArguments().getString(DIALOG_TAG), intentForPassingData);

            dismiss();
        }
    };

}
