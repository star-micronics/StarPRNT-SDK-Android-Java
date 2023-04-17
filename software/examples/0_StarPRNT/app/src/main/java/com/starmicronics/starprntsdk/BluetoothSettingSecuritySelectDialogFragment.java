package com.starmicronics.starprntsdk;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class BluetoothSettingSecuritySelectDialogFragment extends CommonAlertDialogFragment {
    private static final String SECURITY_TAG = "bundle_string_";

    private Callback mCallbackTarget;

    public static BluetoothSettingSecuritySelectDialogFragment newInstance(String tag, List<String> securityList) {
        BluetoothSettingSecuritySelectDialogFragment dialogFragment = new BluetoothSettingSecuritySelectDialogFragment();

        Bundle args = new Bundle();
        args.putString(DIALOG_TAG, tag);

        for (int i = 0; i < securityList.size(); i++){
            args.putString(SECURITY_TAG + i, securityList.get(i));
        }

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

        List<String> securityList = new ArrayList<>();
        int i = 0;
        while (true) {
            String arg = args.getString(SECURITY_TAG + i);

            if (arg == null){
                break;
            }

            securityList.add(arg);
            i++;
        }

        String[] array = securityList.toArray(new String[securityList.size()]);

        builder.setItems(array,
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

            selectedSecurity = args.getString(SECURITY_TAG + which);

            Intent intentForPassingData = new Intent();
            intentForPassingData.putExtra(CommonActivity.BUNDLE_KEY_BLUETOOTH_SETTING_SECURITY, selectedSecurity);

            mCallbackTarget.onDialogResult(getArguments().getString(DIALOG_TAG), intentForPassingData);

            dismiss();
        }
    };
}
