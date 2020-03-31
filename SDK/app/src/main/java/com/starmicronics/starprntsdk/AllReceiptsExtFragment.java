package com.starmicronics.starprntsdk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.starmicronics.starprntsdk.Communication.CommunicationResult;
import com.starmicronics.starprntsdk.Communication.Result;

import com.starmicronics.cloudservices.AllReceipts;
import com.starmicronics.cloudservices.RequestCallback;
import com.starmicronics.cloudservices.RequestError;
import com.starmicronics.stario.StarResultCode;
import com.starmicronics.starioextension.ConnectionCallback;
import com.starmicronics.starioextension.StarIoExt.Emulation;
import com.starmicronics.starioextension.StarIoExtManager;
import com.starmicronics.starioextension.StarIoExtManagerListener;
import com.starmicronics.starprntsdk.functions.AllReceiptsFunctions;
import com.starmicronics.starprntsdk.localizereceipts.ILocalizeReceipts;

public class AllReceiptsExtFragment extends Fragment implements CommonAlertDialogFragment.Callback {

    private static final String OPEN_FAILURE_DIALOG = "OpenFailureDialog";

    private ProgressDialog mProgressDialog;

    private TextView mComment;

    private StarIoExtManager mStarIoExtManager;

    private int mSelectedIndex;
    private int mLanguage;

    private boolean mIsFirst = true;
    private boolean mIsPrint;
    private boolean mIsForeground;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        mProgressDialog = new ProgressDialog(getActivity());

        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        Intent intent = getActivity().getIntent();
        mLanguage = intent.getIntExtra(CommonActivity.BUNDLE_KEY_LANGUAGE, PrinterSettingConstant.LANGUAGE_ENGLISH);
        mSelectedIndex = intent.getIntExtra(CommonActivity.BUNDLE_KEY_SELECTED_INDEX, 0);

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        mStarIoExtManager = new StarIoExtManager(StarIoExtManager.Type.Standard, settings.getPortName(), settings.getPortSettings(), 10000, getActivity());     // 10000mS!!!
        mStarIoExtManager.setCashDrawerOpenActiveHigh(settings.getCashDrawerOpenActiveHigh());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_receipts_ext, container, false);

        mComment = rootView.findViewById(R.id.statusTextView);

        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);

        mComment.startAnimation(animation);

        Button testButton = rootView.findViewById(R.id.testPrintButton);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                print();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mIsForeground = true;

        mStarIoExtManager.setListener(mStarIoExtManagerListener);
        mStarIoExtManager.connect(mConnectionCallback);

        mProgressDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.reloadIcon) {
            mProgressDialog.show();

            mStarIoExtManager.disconnect(new ConnectionCallback() {
                @Override
                public void onDisconnected() {
                    if (!mIsForeground) {
                        return;
                    }

                    mStarIoExtManager.connect(mConnectionCallback);
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();

        mIsForeground = false;

        mStarIoExtManager.disconnect(mConnectionCallback);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onDialogResult(String tag, Intent data) {
        // do nothing
    }

    private final ConnectionCallback mConnectionCallback = new ConnectionCallback() {
        @Override
        public void onConnected(boolean result, int resultCode) {
            if (!mIsForeground) {
                return;
            }

            if (!result) {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }

                String message;

                if (resultCode == StarResultCode.FAILURE_IN_USE) {
                    message = "Check the device. (In use)\nThen touch up the Refresh button.";
                }
                else {
                    message = "Check the device. (Power and Bluetooth pairing)\nThen touch up the Refresh button.";
                }

                mComment.setText(message);
                mComment.setTextColor(Color.RED);

                CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance(OPEN_FAILURE_DIALOG);
                dialog.setTitle("Communication Result");
                dialog.setMessage(Communication.getCommunicationResultMessage(new CommunicationResult(Result.ErrorOpenPort, resultCode)));
                dialog.setPositiveButton("OK");
                dialog.show(getChildFragmentManager());

                return;
            }

            if (mIsFirst) {
                mIsFirst = false;

                print();
            }
            else if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }

        @Override
        public void onDisconnected() {
            // do nothing
        }
    };

    private void print() {
        mProgressDialog.show();

        byte[] commands;

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        Emulation emulation = ModelCapability.getEmulation(settings.getModelIndex());
        int paperSize = settings.getPaperSize();

        ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(mLanguage, paperSize);

        int allReceiptSettings = settingManager.getAllReceiptSetting();

        boolean receipt = (allReceiptSettings & 0x00000001) != 0x00000000;
        boolean info    = (allReceiptSettings & 0x00000002) != 0x00000000;
        boolean qrCode  = (allReceiptSettings & 0x00000004) != 0x00000000;

        mIsPrint = receipt || info || qrCode;

        switch (mSelectedIndex) {
            case 1:
                commands = AllReceiptsFunctions.createTextReceiptData(getActivity(), emulation, localizeReceipts, paperSize, false, receipt, info, qrCode, mAllReceiptsCallback);
                break;
            case 2:
                commands = AllReceiptsFunctions.createTextReceiptData(getActivity(), emulation, localizeReceipts, paperSize, true, receipt, info, qrCode, mAllReceiptsCallback);
                break;
            case 3:
                commands = AllReceiptsFunctions.createRasterReceiptData(getActivity(), emulation, localizeReceipts, paperSize, receipt, info, qrCode, mAllReceiptsCallback);
                break;
            case 4:
                commands = AllReceiptsFunctions.createScaleRasterReceiptData(getActivity(), emulation, localizeReceipts, paperSize, true, receipt, info, qrCode, mAllReceiptsCallback);
                break;
//          case 5:
            default:
                commands = AllReceiptsFunctions.createScaleRasterReceiptData(getActivity(), emulation, localizeReceipts, paperSize, false, receipt, info, qrCode, mAllReceiptsCallback);
                break;
        }

        if (commands == null) {
            commands = new byte[0];
        }

        Communication.sendCommands(mStarIoExtManager, commands, mStarIoExtManager.getPort(), 30000, mCallback);
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

            if (!mIsPrint) {
                return;
            }

            CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance("CommResultDialog");
            dialog.setTitle("Communication Result");
            dialog.setMessage(Communication.getCommunicationResultMessage(communicationResult));
            dialog.setPositiveButton("OK");
            dialog.show(getChildFragmentManager());
        }
    };

    private final RequestCallback mAllReceiptsCallback = new RequestCallback() {
        @Override
        public void onRequestResult(int statusCode, RequestError requestError) {
            if (!mIsForeground) {
                return;
            }

            String message;

            if (requestError != null) {
                message = requestError.getMessage();
            }
            else {
                message = "Status Code : " + statusCode;
            }

            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT)
                 .show();
        }
    };

    private final StarIoExtManagerListener mStarIoExtManagerListener = new StarIoExtManagerListener() {
        @Override
        public void onPrinterImpossible() {
            mComment.setText("Printer Impossible.");

            mComment.setTextColor(Color.RED);
        }

        @Override
        public void onPrinterOnline() {
            mComment.setText("Printer Online.");

            mComment.setTextColor(Color.BLUE);
        }

        @Override
        public void onPrinterOffline() {
//          mComment.setText("Printer Offline.");
//
//          mComment.setTextColor(Color.RED);
        }

        @Override
        public void onPrinterPaperReady() {
//          mComment.setText("Printer Paper Ready.");
//
//          mComment.setTextColor(Color.BLUE);
        }

        @Override
        public void onPrinterPaperNearEmpty() {
            mComment.setText("Printer Paper Near Empty.");

            mComment.setTextColor(0xffffa500);     // Orange
        }

        @Override
        public void onPrinterPaperEmpty() {
            mComment.setText("Printer Paper Empty.");

            mComment.setTextColor(Color.RED);
        }

        @Override
        public void onPrinterCoverOpen() {
            mComment.setText("Printer Cover Open.");

            mComment.setTextColor(Color.RED);
        }

        @Override
        public void onPrinterCoverClose() {
//          mComment.setText("Printer Cover Close.");
//
//          mComment.setTextColor(Color.BLUE);
        }

        @Override
        public void onAccessoryConnectSuccess() {
            mComment.setText("Accessory Connect Success.");

            mComment.setTextColor(Color.BLUE);
        }

        @Override
        public void onAccessoryConnectFailure() {
            mComment.setText("Accessory Connect Failure.");

            mComment.setTextColor(Color.RED);
        }

        @Override
        public void onAccessoryDisconnect() {
            mComment.setText("Accessory Disconnect.");

            mComment.setTextColor(Color.RED);
        }

        @Override
        public void onStatusUpdate(String status) {
            AllReceipts.updateStatus(getContext(), status, new RequestCallback() {
                @Override
                public void onRequestResult(int statusCode, RequestError requestError) {
                    if (!mIsForeground) {
                        return;
                    }

                    String message;

                    if (requestError != null) {
                        message = requestError.getMessage();
                    }
                    else {
                        message = "Status Code : " + statusCode;
                    }

                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT)
                         .show();
                }
            });
        }
    };
}
