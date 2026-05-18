package com.starmicronics.starprntsdk;

import androidx.appcompat.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;

public class InterfaceSelectDialogFragment extends CommonAlertDialogFragment {

    private CommonAlertDialogFragment.Callback mCallbackTarget;

    public static InterfaceSelectDialogFragment newInstance(String tag) {
        InterfaceSelectDialogFragment dialogFragment = new InterfaceSelectDialogFragment();

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
    public @NonNull Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Select interface.");

        builder.setItems(new String[]{
                        "LAN",
                        "Bluetooth",
                        "USB",
                        "All",
                        "Manual"},
                        mOnInterfaceClickListener);

        setupNegativeButton(builder);

        mCallbackTarget = (CommonAlertDialogFragment.Callback) getParentFragment();

        return builder.create();
    }

    private DialogInterface.OnClickListener mOnInterfaceClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String[] selectedInterfaceArray;

            switch (which) {
                case 0:
                    selectedInterfaceArray = new String[] {PrinterSettingConstant.IF_TYPE_ETHERNET};
                    break;
                case 1:
                    selectedInterfaceArray = new String[] {PrinterSettingConstant.IF_TYPE_BLUETOOTH};
                    break;
                case 2:
                    selectedInterfaceArray = new String[] {PrinterSettingConstant.IF_TYPE_USB};
                    break;
                case 3:
                default:
                    selectedInterfaceArray = new String[] {PrinterSettingConstant.IF_TYPE_ETHERNET, PrinterSettingConstant.IF_TYPE_BLUETOOTH, PrinterSettingConstant.IF_TYPE_USB};
                    break;
                case 4:
                    selectedInterfaceArray = new String[] {PrinterSettingConstant.IF_TYPE_MANUAL};
                    break;
            }

            Intent intentForPassingData = new Intent();
            intentForPassingData.putExtra(CommonActivity.BUNDLE_KEY_INTERFACE, selectedInterfaceArray);

            mCallbackTarget.onDialogResult(getArguments().getString(DIALOG_TAG), intentForPassingData);

            dismiss();
        }
    };
}
