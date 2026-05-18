package com.starmicronics.starprntsdk;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class ModelConfirmDialogFragment extends CommonAlertDialogFragment {

    public static ModelConfirmDialogFragment newInstance(String tag, int model) {
        ModelConfirmDialogFragment dialogFragment = new ModelConfirmDialogFragment();

        Bundle args = new Bundle();
        args.putString(DIALOG_TAG, tag);
        args.putBoolean(CANCEL, false);
        args.putBoolean(CALLBACK, true);
        args.putString(LABEL_POSITIVE, "Yes");
        args.putString(LABEL_NEGATIVE, "No");
        args.putInt(CommonActivity.BUNDLE_KEY_MODEL_INDEX, model);

        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @Override
    public @NonNull
    Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle args = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        String modelTitle = ModelCapability.getModelTitle(getArguments().getInt(CommonActivity.BUNDLE_KEY_MODEL_INDEX));

        builder.setTitle("Confirm.");
        builder.setMessage("Is your printer " + modelTitle + " ?");

        builder.setPositiveButton(args.getString(LABEL_POSITIVE), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intentForPassingData = new Intent();
                intentForPassingData.putExtra(LABEL_POSITIVE, LABEL_POSITIVE);
                intentForPassingData.putExtra(CommonActivity.BUNDLE_KEY_MODEL_INDEX, args.getInt(CommonActivity.BUNDLE_KEY_MODEL_INDEX));

                callbackToTarget(args.getString(DIALOG_TAG), intentForPassingData);

                dialog.dismiss();
            }
        });

        builder.setNegativeButton(args.getString(LABEL_NEGATIVE), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intentForPassingData = new Intent();
                intentForPassingData.putExtra(LABEL_NEGATIVE, LABEL_NEGATIVE);

                callbackToTarget(args.getString(DIALOG_TAG), intentForPassingData);

                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
