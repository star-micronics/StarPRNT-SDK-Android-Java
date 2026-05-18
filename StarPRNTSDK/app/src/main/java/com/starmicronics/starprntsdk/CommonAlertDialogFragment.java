package com.starmicronics.starprntsdk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CommonAlertDialogFragment extends DialogFragment {

    public interface Callback {
        void onDialogResult(String tag, Intent data);
    }

    public static final String DIALOG_TAG     = "bundle_dialog_tag";

    protected static final String TITLE          = "bundle_title";
    protected static final String MESSAGE_STRING = "bundle_message_string";
    public    static final String LABEL_POSITIVE = "bundle_label_positive";
    public    static final String LABEL_NEGATIVE = "bundle_label_negative";
    public    static final String CANCEL         = "bundle_cancel";
    protected static final String CALLBACK       = "bundle_callback";

    public CommonAlertDialogFragment() {

    }

    public static CommonAlertDialogFragment newInstance(String tag) {
        CommonAlertDialogFragment dialogFragment = new CommonAlertDialogFragment();

        Bundle args = new Bundle();
        args.putString(DIALOG_TAG, tag);
        args.putBoolean(CANCEL, true);
        args.putBoolean(CALLBACK, true);

        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        setupTitle(builder);
        setupMessage(builder);
        setupPositiveButton(builder);
        setupNegativeButton(builder);

        return builder.create();
    }

    protected void setupTitle(AlertDialog.Builder builder) {
        final Bundle args = getArguments();
        builder.setTitle(args.getString(TITLE, "UNKNOWN_TITLE"));
    }

    protected void setupMessage(AlertDialog.Builder builder) {
        final Bundle args = getArguments();

        if (args.containsKey(MESSAGE_STRING)) {
            builder.setMessage(args.getString(MESSAGE_STRING, "UNKNOWN_MESSAGE_STRING"));
        }
    }

    protected void setupPositiveButton(AlertDialog.Builder builder) {
        final Bundle args = getArguments();
        final boolean isCallback = args.getBoolean(CALLBACK, false);

        if (args.containsKey(LABEL_POSITIVE)) {
            builder.setPositiveButton(args.getString(LABEL_POSITIVE), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (isCallback) {
                        Intent intentForPassingData = new Intent();
                        intentForPassingData.putExtra(LABEL_POSITIVE, LABEL_POSITIVE);
                        callbackToTarget(args.getString(DIALOG_TAG), intentForPassingData);
                    }

                    dialog.dismiss();
                }
            });
        }
    }

    protected void setupNegativeButton(AlertDialog.Builder builder) {
        final Bundle args = getArguments();
        final boolean isCallback = args.getBoolean(CALLBACK, false);

        if (args.containsKey(LABEL_NEGATIVE)) {
            builder.setNegativeButton(args.getString(LABEL_NEGATIVE), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (isCallback) {
                        Intent intentForPassingData = new Intent();
                        intentForPassingData.putExtra(LABEL_NEGATIVE, LABEL_NEGATIVE);
                        callbackToTarget(args.getString(DIALOG_TAG), intentForPassingData);
                    }

                    dialog.dismiss();
                }
            });
        }
    }

    public void setTitle(String title) {
        getArguments().putString(TITLE, title);
    }

    public void setMessage(String message) {
        getArguments().putString(MESSAGE_STRING, message);
    }

    public void setPositiveButton(String label) {
        getArguments().putString(LABEL_POSITIVE, label);
    }

    public void setNegativeButton(String label) {
        getArguments().putString(LABEL_NEGATIVE, label);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            String tag = getArguments().getString(DIALOG_TAG);

            FragmentManager manager;

            if (getParentFragment() != null) {
                manager = getParentFragment().getChildFragmentManager();
            }
            else if (getActivity() != null) {
                manager = getFragmentManager();
            }
            else {
                return;
            }

            FragmentTransaction ft   = manager.beginTransaction();
            Fragment prev = manager.findFragmentByTag(tag);

            if (prev != null) {
                ft.remove(prev);
            }

            ft.commit();
        }
    }

    public void show(FragmentManager manager) {
        String tag = getArguments().getString(DIALOG_TAG, "TAG_HAS_NOT_BEEN_SPECIFIED");

        if (tag.equals("TAG_HAS_NOT_BEEN_SPECIFIED")) {
            throw new RuntimeException("The tag of this dialog has not been specified.");
        }

        show(manager, tag);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);

        Bundle args = getArguments();
        boolean isCallback = args.getBoolean(CALLBACK, false);

        if (args.containsKey(CANCEL) && args.getBoolean(CANCEL, false)) {
            if (isCallback) {
                Intent intentForPassingData = new Intent();
                intentForPassingData.putExtra(CANCEL, CANCEL);
                callbackToTarget(args.getString(DIALOG_TAG), intentForPassingData);
            }

            dialog.dismiss();
        }
    }

    protected void callbackToTarget(String tag, Intent intentForPassingData) {
        Callback callback;

        if (getParentFragment() instanceof Callback) {
            callback = (Callback) getParentFragment();
        }
        else if (getActivity() instanceof Callback) {
            callback = (Callback) getActivity();
        }
        else {
            throw new ClassCastException("Target must implement Callback.");
        }

        callback.onDialogResult(tag, intentForPassingData);
    }
}
