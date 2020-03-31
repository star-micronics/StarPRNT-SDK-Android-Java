package com.starmicronics.starprntsdk;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class BluetoothSettingSecuritySelectDialogFragment extends CommonAlertDialogFragment {

    private static final String SECURITY1 = "bundle_string1";
    private static final String SECURITY2 = "bundle_string2";

    private Callback mCallbackTarget;

    public static BluetoothSettingSecuritySelectDialogFragment newInstance(String tag, String security1, String security2) {
        BluetoothSettingSecuritySelectDialogFragment dialogFragment = new BluetoothSettingSecuritySelectDialogFragment();

        Bundle args = new Bundle();
        args.putString(DIALOG_TAG, tag);
        args.putString(SECURITY1, security1);
        args.putString(SECURITY2, security2);
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

        builder.setTitle("Select security type.");

        Bundle args = getArguments();

        builder.setItems(new String[]{
                        args.getString(SECURITY1),
                        args.getString(SECURITY2)},
                        mOnSecurityClickListener);

        setupNegativeButton(builder);

        mCallbackTarget = (Callback) getParentFragment();

        return builder.create();
    }

    private DialogInterface.OnClickListener mOnSecurityClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String selectedSecurity = "";

            Bundle args = getArguments();

            switch (which) {
                case 0: selectedSecurity = args.getString(SECURITY1); break;
                case 1: selectedSecurity = args.getString(SECURITY2); break;
            }

            Intent intentForPassingData = new Intent();
            intentForPassingData.putExtra(CommonActivity.BUNDLE_KEY_BLUETOOTH_SETTING_SECURITY, selectedSecurity);

            mCallbackTarget.onDialogResult(getArguments().getString(DIALOG_TAG), intentForPassingData);

            dismiss();
        }
    };
}
