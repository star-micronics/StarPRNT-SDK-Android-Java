package com.starmicronics.starprntsdk;

import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.text.InputType;
import android.widget.EditText;

public class PortNameDialogFragment extends CommonAlertDialogFragment {

    public PortNameDialogFragment() {
        // Required empty public constructor
    }

    public static PortNameDialogFragment newInstance(String tag, String portName) {
        PortNameDialogFragment dialogFragment = new PortNameDialogFragment();

        Bundle args = new Bundle();
        args.putString(DIALOG_TAG, tag);
        args.putBoolean(CANCEL, false);
        args.putBoolean(CALLBACK, true);
        args.putString(LABEL_POSITIVE, "OK");
        args.putString(LABEL_NEGATIVE, "Cancel");
        args.putString(CommonActivity.BUNDLE_KEY_PORT_NAME, portName);

        dialogFragment.setArguments(args);
        dialogFragment.setCancelable(false);

        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle args = getArguments();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Please enter the port name.");

        setupNegativeButton(builder);

        final EditText editText = new EditText(getActivity());
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setText(args.getString(CommonActivity.BUNDLE_KEY_PORT_NAME));

        builder.setView(editText);

        builder.setPositiveButton(args.getString(LABEL_POSITIVE), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intentForPassingData = new Intent();
                intentForPassingData.putExtra(LABEL_POSITIVE, LABEL_POSITIVE);
                intentForPassingData.putExtra(CommonActivity.BUNDLE_KEY_PORT_NAME, editText.getText().toString());

                callbackToTarget(args.getString(DIALOG_TAG), intentForPassingData);

                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
