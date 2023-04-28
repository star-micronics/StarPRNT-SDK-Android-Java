package com.starmicronics.starprntsdk;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class PaperSizeSelectDialogFragment extends CommonAlertDialogFragment {

    private CommonAlertDialogFragment.Callback mCallbackTarget;

    public static PaperSizeSelectDialogFragment newInstance(String tag) {
        PaperSizeSelectDialogFragment dialogFragment = new PaperSizeSelectDialogFragment();

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

        builder.setTitle("Select paper size.");

        builder.setItems(new String[]{
                        "2\" (384dots)",
                        "3\" (576dots)",
                        "4\" (832dots)"},
                        mOnPaperSizeClickListener);

        setupNegativeButton(builder);

        mCallbackTarget = (CommonAlertDialogFragment.Callback) getParentFragment();

        return builder.create();
    }

    private DialogInterface.OnClickListener mOnPaperSizeClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            int selectedPaperSize = PrinterSettingConstant.PAPER_SIZE_THREE_INCH;

            switch (which) {
                case 0: selectedPaperSize = PrinterSettingConstant.PAPER_SIZE_TWO_INCH; break;
                case 1: selectedPaperSize = PrinterSettingConstant.PAPER_SIZE_THREE_INCH; break;
                case 2: selectedPaperSize = PrinterSettingConstant.PAPER_SIZE_FOUR_INCH; break;
            }

            Intent intentForPassingData = new Intent();
            intentForPassingData.putExtra(CommonActivity.BUNDLE_KEY_PAPER_SIZE, selectedPaperSize);

            mCallbackTarget.onDialogResult(getArguments().getString(DIALOG_TAG), intentForPassingData);

            dismiss();
        }
    };
}
