package com.starmicronics.starprntsdk;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class LicenseDialogFragment extends CommonAlertDialogFragment {

    public static LicenseDialogFragment newInstance(String tag) {
        LicenseDialogFragment dialogFragment = new LicenseDialogFragment();

        Bundle args = new Bundle();
        args.putString(DIALOG_TAG, tag);
        args.putBoolean(CANCEL, false);
        args.putBoolean(CALLBACK, true);
        args.putString(LABEL_POSITIVE, "OK");

        dialogFragment.setArguments(args);
        dialogFragment.setCancelable(true);

        return dialogFragment;
    }

    @Override
    public @NonNull
    Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Open Source License");

        builder.setMessage(
                "This application contains deliverables distributed under license of Apache License, Version 2.0 (" +
                "http://www.apache.org/licenses/LICENSE-2.0).\n" +
                "\n" +
                "Notices for libraries:\n" +
                "[ZXing]\n" +
                "------------------------------\n" +
                "NOTICES FOR BARCODE 4J\n" +
                "------------------------------\n" +
                "\n" +
                "Barcode 4J\n" +
                "Copyright 2002-2010 Jeremias Märki\n" +
                "Copyright 2005-2006 Dietmar Bürkle\n" +
                "\n" +
                "Portions of this software were contributed under section 5 of the\n" +
                "Apache License. Contributors are listed under:\n" +
                "http://barcode4j.sourceforge.net/contributors.html\n" +
                "\n" +
                "------------------------------\n" +
                "NOTICES FOR JCOMMANDER\n" +
                "------------------------------\n" +
                "\n" +
                "Copyright 2010 Cedric Beust cedric@beust.com");

        setupPositiveButton(builder);

        return builder.create();
    }
}
