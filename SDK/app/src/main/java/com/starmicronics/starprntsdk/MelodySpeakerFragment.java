package com.starmicronics.starprntsdk;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.starmicronics.starprntsdk.Communication.CommunicationResult;
import com.starmicronics.starprntsdk.Communication.Result;

import com.starmicronics.starioextension.IMelodySpeakerCommandBuilder.SoundStorageArea;
import com.starmicronics.starioextension.IPeripheralConnectParser;
import com.starmicronics.starioextension.SoundSetting;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starioextension.StarIoExt.MelodySpeakerModel;

import com.starmicronics.starprntsdk.functions.MelodySpeakerFunctions;

public class MelodySpeakerFragment extends CommonFragment implements CommonAlertDialogFragment.Callback {

    private static final String ERROR_DIALOG = "ErrorDialog";

    private static final int STORAGE_PERMISSION_REQUEST_CODE = 0x0001;
    private static final int OPEN_FILE_REQUEST_CODE          = 0x0002;

    static final private String SOUND_STORAGE_AREA_ITEM[] = {
            "Default",
            "Area1",
            "Area2",
    };

    static final private String VOLUME_ITEM[] = {
            "Default",
            "Off",
            "Min",
            "Max",
            "Manual",
    };

    private ProgressDialog mProgressDialog;

    private boolean mIsForeground;

    private View mRegisteredSoundLayout;
    private View mReceivedDataLayout;

    private Spinner mSoundStorageAreaSpinner;

    private EditText mSoundNumberEditText;

    private Spinner mRegisteredSoundVolumeSpinner;

    private LinearLayout mRegisteredSoundVolumeSeekBarContainer;
    private SeekBar mRegisteredSoundVolumeSeekBar;
    private TextView mRegisteredSoundVolumeTextView;

    private TextView mFileNameTextView;

    private Spinner mReceivedDataVolumeSpinner;

    private LinearLayout mReceivedDataVolumeSeekBarContainer;
    private SeekBar mReceivedDataVolumeSeekBar;
    private TextView mReceivedDataVolumeTextView;

    private MelodySpeakerModel mMelodySpeakerModel;

    private Uri mUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_melody_speaker, container, false);

        RadioGroup layoutSelectRadioGroup = rootView.findViewById(R.id.layoutSelectRadioGroup);

        layoutSelectRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.registeredSoundLayoutRadioButton) {
                    mRegisteredSoundLayout.setVisibility(View.VISIBLE);
                    mReceivedDataLayout.setVisibility(View.GONE);
                }
                else if (checkedId == R.id.receivedDataLayoutRadioButton) {
                    mRegisteredSoundLayout.setVisibility(View.GONE);
                    mReceivedDataLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        // Registered Sound Layout
        mRegisteredSoundLayout = rootView.findViewById(R.id.registeredSoundLayout);

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        mSoundStorageAreaSpinner = rootView.findViewById(R.id.soundStorageAreaSpinner);

        ArrayAdapter<String> soundNumberArrayAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                SOUND_STORAGE_AREA_ITEM);

        soundNumberArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSoundStorageAreaSpinner.setAdapter(soundNumberArrayAdapter);

        mSoundStorageAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSoundNumberEditText.setEnabled(position != 0);     // position == 0 means "Default"
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSoundNumberEditText = rootView.findViewById(R.id.soundNumberEditText);

        int modelIndex = settings.getModelIndex();
        int defaultSoundNumber = ModelCapability.getDefaultSoundNumber(modelIndex);

        mSoundNumberEditText.setText(String.valueOf(defaultSoundNumber));

        mRegisteredSoundVolumeSpinner = rootView.findViewById(R.id.registeredSoundVolumeSpinner);

        mRegisteredSoundVolumeSpinner.setEnabled(modelIndex == ModelCapability.MC_PRINT3 || modelIndex == ModelCapability.TSP100IV);

        ArrayAdapter<String> volumeArrayAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                VOLUME_ITEM);

        volumeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mRegisteredSoundVolumeSpinner.setAdapter(volumeArrayAdapter);

        mRegisteredSoundVolumeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 4) {    // position == 4 means "Manual"
                    mRegisteredSoundVolumeSeekBarContainer.setVisibility(View.VISIBLE);
                }
                else {
                    mRegisteredSoundVolumeSeekBarContainer.setVisibility(View.GONE);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mRegisteredSoundVolumeSeekBarContainer = rootView.findViewById(R.id.registeredSoundVolumeSeekBarContainer);
        mRegisteredSoundVolumeSeekBarContainer.setVisibility(View.GONE);

        mRegisteredSoundVolumeSeekBar = rootView.findViewById(R.id.registeredSoundVolumeSeekBar);

        mRegisteredSoundVolumeTextView = rootView.findViewById(R.id.registerdSoundVolumeTextView);

        int defaultVolume = ModelCapability.getDefaultVolume(modelIndex);

        if (defaultVolume != -1) {
            mRegisteredSoundVolumeSeekBar.setProgress(defaultVolume);

            mRegisteredSoundVolumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mRegisteredSoundVolumeTextView.setText(String.valueOf(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });

            mRegisteredSoundVolumeTextView.setText(String.valueOf(defaultVolume));
        }
        else {
            mRegisteredSoundVolumeSeekBar.setEnabled(false);
        }

        Button registeredSoundPlayButton = rootView.findViewById(R.id.registeredSoundPlayButton);

        registeredSoundPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRegisteredSound();
            }
        });

        // Received Data Layout
        mReceivedDataLayout = rootView.findViewById(R.id.receivedDataLayout);

        mReceivedDataLayout.setVisibility(View.GONE);

        mFileNameTextView = rootView.findViewById(R.id.fileNameTextView);

        mReceivedDataVolumeSpinner = rootView.findViewById(R.id.receivedDataVolumeSpinner);

        mReceivedDataVolumeSpinner.setEnabled(modelIndex == ModelCapability.MC_PRINT3 || modelIndex == ModelCapability.TSP100IV);

        ArrayAdapter<String> receivedDataVolumeArrayAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                VOLUME_ITEM);

        receivedDataVolumeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mReceivedDataVolumeSpinner.setAdapter(receivedDataVolumeArrayAdapter);

        mReceivedDataVolumeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 4) {    // position == 4 means "Manual"
                    mReceivedDataVolumeSeekBarContainer.setVisibility(View.VISIBLE);
                }
                else {
                    mReceivedDataVolumeSeekBarContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mReceivedDataVolumeSeekBarContainer = rootView.findViewById(R.id.receivedDataVolumeSeekBarContainer);
        mReceivedDataVolumeSeekBarContainer.setVisibility(View.GONE);

        mReceivedDataVolumeSeekBar = rootView.findViewById(R.id.receivedDataVolumeSeekBar);

        mReceivedDataVolumeTextView = rootView.findViewById(R.id.receivedDataVolumeTextView);

        if (defaultVolume != -1) {
            mReceivedDataVolumeSeekBar.setProgress(defaultVolume);

            mReceivedDataVolumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mReceivedDataVolumeTextView.setText(String.valueOf(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });

            mReceivedDataVolumeTextView.setText(String.valueOf(defaultVolume));
        }
        else {
            mReceivedDataVolumeSeekBar.setEnabled(false);
        }

        Button selectButton = rootView.findViewById(R.id.audioFileSelectButton);

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileListAfterPermissionCheck();
            }
        });

        Button receivedDataPlayButton = rootView.findViewById(R.id.receivedDataPlayButton);

        receivedDataPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playReceivedData();
            }
        });

        switch (modelIndex) {
            default:
            case ModelCapability.MC_PRINT3:
            case ModelCapability.TSP100IV:
                mMelodySpeakerModel = MelodySpeakerModel.MCS10;
                break;
            case ModelCapability.FVP10:
                mMelodySpeakerModel = MelodySpeakerModel.FVP10;
                break;
        }

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mIsForeground = true;
    }

    @Override
    public void onPause() {
        super.onPause();

        mIsForeground = false;
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    private void playRegisteredSound() {
        mProgressDialog.show();

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        if (mMelodySpeakerModel == MelodySpeakerModel.MCS10) {
            final IPeripheralConnectParser parser = StarIoExt.createMelodySpeakerConnectParser(MelodySpeakerModel.MCS10);

            Communication.parseDoNotCheckCondition(MelodySpeakerFragment.class, parser, settings.getPortName(), settings.getPortSettings(), 10000, getActivity(), new Communication.SendCallback() {
                @Override
                public void onStatus(CommunicationResult communicationResult) {
                    if (!mIsForeground) {
                        return;
                    }

                    String msg;
                    String title;

                    if (communicationResult.getResult() == Result.Success) {
                        if (parser.isConnected()) {
                            sendRegisteredSoundCommand();
                            return;
                        }
                        else {
                            title = "Check Status";
                            msg   = "MelodySpeaker Disconnect";
                        }
                    }
                    else {
                        title = "Communication Result";
                        msg   = Communication.getCommunicationResultMessage(communicationResult);
                    }

                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                    }

                    CommonAlertDialogFragment dialog2 = CommonAlertDialogFragment.newInstance(ERROR_DIALOG);
                    dialog2.setTitle(title);
                    dialog2.setMessage(msg);
                    dialog2.setPositiveButton("OK");
                    dialog2.show(getChildFragmentManager());
                }
            });
        }
        else {
            sendRegisteredSoundCommand();
        }
    }

    private void sendRegisteredSoundCommand() {
        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        SoundStorageArea soundStorageArea = null;
        boolean specifySound = false;

        switch (mSoundStorageAreaSpinner.getSelectedItemPosition()) {
            default:
            case 0:
                // Not specify Sound Storage Area and Sound Number.
                break;
            case 1:
                soundStorageArea = SoundStorageArea.Area1;
                specifySound = true;
                break;
            case 2:
                soundStorageArea = SoundStorageArea.Area2;
                specifySound = true;
                break;
        }

        int soundNumber;

        try {
            soundNumber = Integer.parseInt(mSoundNumberEditText.getText().toString());
        }
        catch (NumberFormatException e) {
            soundNumber = 0;
        }

        int volume = 0;
        boolean specifyVolume = false;

        switch (mRegisteredSoundVolumeSpinner.getSelectedItemPosition()) {
            default:
            case 0:
                // Not specify Volume.
                break;
            case 1:
                volume = SoundSetting.VOLUME_OFF;
                specifyVolume = true;
                break;
            case 2:
                volume = SoundSetting.VOLUME_MIN;
                specifyVolume = true;
                break;
            case 3:
                volume = SoundSetting.VOLUME_MAX;
                specifyVolume = true;
                break;
            case 4:
                volume = mRegisteredSoundVolumeSeekBar.getProgress();
                specifyVolume = true;
                break;
        }

        byte[] commands;

        try {
            commands = MelodySpeakerFunctions.createPlayingRegisteredSound(mMelodySpeakerModel, soundStorageArea, specifySound, soundNumber, specifyVolume, volume);
        }
        catch (IllegalArgumentException | UnsupportedOperationException e) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(ERROR_DIALOG);
            dialog.setTitle("Error");
            dialog.setMessage(e.getMessage());
            dialog.setPositiveButton("OK");
            dialog.show(getChildFragmentManager());
            return;
        }

        Communication.sendCommands(this, commands, settings.getPortName(), settings.getPortSettings(), 10000, 30000, getActivity(), mCallback);     // 10000mS!!!
    }

    private void playReceivedData() {
        if (mUri == null) {
            CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(ERROR_DIALOG);
            dialog.setTitle("Error");
            dialog.setMessage("Sound file is not selected.");
            dialog.setPositiveButton("OK");
            dialog.show(getChildFragmentManager());
            return;
        }

        mProgressDialog.show();

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        if (mMelodySpeakerModel == MelodySpeakerModel.MCS10) {
            final IPeripheralConnectParser parser = StarIoExt.createMelodySpeakerConnectParser(MelodySpeakerModel.MCS10);

            Communication.parseDoNotCheckCondition(MelodySpeakerFragment.class, parser, settings.getPortName(), settings.getPortSettings(), 10000, getActivity(), new Communication.SendCallback() {
                @Override
                public void onStatus(CommunicationResult communicationResult) {
                    if (!mIsForeground) {
                        return;
                    }

                    String msg;
                    String title;

                    if (communicationResult.getResult() == Result.Success) {
                        if (parser.isConnected()) {
                            sendReceivedDataCommand();
                            return;
                        }
                        else {
                            title = "Check Status";
                            msg   = "MelodySpeaker Disconnect";
                        }
                    }
                    else {
                        title = "Communication Result";
                        msg   = Communication.getCommunicationResultMessage(communicationResult);
                    }

                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                    }

                    CommonAlertDialogFragment dialog2 = CommonAlertDialogFragment.newInstance(ERROR_DIALOG);
                    dialog2.setTitle(title);
                    dialog2.setMessage(msg);
                    dialog2.setPositiveButton("OK");
                    dialog2.show(getChildFragmentManager());
                }
            });
        }
        else {
            sendReceivedDataCommand();
        }
    }

    private void sendReceivedDataCommand() {
        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        int volume = 0;
        boolean specifyVolume = false;

        switch (mReceivedDataVolumeSpinner.getSelectedItemPosition()) {
            default:
            case 0:
                // Not specify Volume.
                break;
            case 1:
                volume = SoundSetting.VOLUME_OFF;
                specifyVolume = true;
                break;
            case 2:
                volume = SoundSetting.VOLUME_MIN;
                specifyVolume = true;
                break;
            case 3:
                volume = SoundSetting.VOLUME_MAX;
                specifyVolume = true;
                break;
            case 4:
                volume = mReceivedDataVolumeSeekBar.getProgress();
                specifyVolume = true;
                break;
        }

        boolean isCommandCreated = false;
        String errorMessage = null;
        byte[] commands = null;

        try {
            commands = MelodySpeakerFunctions.createPlayingReceivedData(mMelodySpeakerModel, mUri, specifyVolume, volume, getActivity());

            if (commands != null) {
                isCommandCreated = true;
            }
            else {
                errorMessage = "File input error.";
            }
        }
        catch (IllegalArgumentException | UnsupportedOperationException e) {
            errorMessage = e.getMessage();
        }

        if (!isCommandCreated) {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(ERROR_DIALOG);
            dialog.setTitle("Error");
            dialog.setMessage(errorMessage);
            dialog.setPositiveButton("OK");
            dialog.show(getChildFragmentManager());
            return;
        }

        Communication.sendCommands(this, commands, settings.getPortName(), settings.getPortSettings(), 10000, 30000, getActivity(), mCallback);     // 10000mS!!!
    }

    private final Communication.SendCallback mCallback = new Communication.SendCallback() {
        @Override
        public void onStatus(CommunicationResult communicationResult) {
            if (!mIsForeground) {
                return;
            }

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance("CommResultDialog");
            dialog.setTitle("Communication Result");
            dialog.setMessage(Communication.getCommunicationResultMessage(communicationResult));
            dialog.setPositiveButton("OK");
            dialog.show(getChildFragmentManager());
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (!(requestCode == OPEN_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK)) {
            return;
        }

        mUri = data.getData();

        String fileName = getFileName(mUri);

        mFileNameTextView.setText(fileName);
    }

    private void openFileListAfterPermissionCheck() {
        String permissionType = "";
        if (Build.VERSION.SDK_INT > 32) {
            permissionType = Manifest.permission.READ_MEDIA_AUDIO;
        }
        else {
            permissionType = Manifest.permission.READ_EXTERNAL_STORAGE;
        }

        int hasPermission = ContextCompat.checkSelfPermission(getActivity(), permissionType);

        if (hasPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{permissionType}, STORAGE_PERMISSION_REQUEST_CODE);
        } else {
            openFileList();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode != STORAGE_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openFileList();
        }
        else {
            Toast.makeText(getActivity(), "Allow storage from Setting -> App -> StarPRNT SDK -> Permission", Toast.LENGTH_LONG)
                 .show();
        }
    }

    private void openFileList() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);

        intent.setType("audio/*");

        try {
            startActivityForResult(intent, OPEN_FILE_REQUEST_CODE);
        }
        catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "Install some app to browse and open sound files.", Toast.LENGTH_LONG)
                 .show();
        }
    }

    private String getFileName(Uri uri) {
        String fileName = null;

        if (uri.getScheme().equals("content")) {
            Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    fileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
            finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        if (fileName == null) {
            fileName = uri.getPath();
            int startIndex = fileName.lastIndexOf('/');

            if (startIndex != -1) {
                fileName = fileName.substring(startIndex + 1);
            }
        }

        return fileName;
    }

    @Override
    public void onDialogResult(String tag, Intent data) {
        // do nothing
    }
}
