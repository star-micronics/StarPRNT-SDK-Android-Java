package com.starmicronics.starprntsdk;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.starmicronics.stario.StarResultCode;
import com.starmicronics.starioextension.ConnectionCallback;
import com.starmicronics.starioextension.StarIoExt.Emulation;
import com.starmicronics.starioextension.StarIoExtManager;
import com.starmicronics.starioextension.StarIoExtManagerListener;
import com.starmicronics.starprntsdk.Communication.CommunicationResult;
import com.starmicronics.starprntsdk.Communication.Result;
import com.starmicronics.starprntsdk.functions.PrinterFunctions;
import com.starmicronics.starprntsdk.localizereceipts.ILocalizeReceipts;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AutoSwitchInterfaceExtFragment extends Fragment implements CommonAlertDialogFragment.Callback  {

    private static final String OPEN_FAILURE_DIALOG = "OpenFailureDialog";

    private static final int LIST_MAX_NUM = 30;

    private ProgressDialog mProgressDialog;

    private List<ItemList>  mList;
    private ItemListAdapter mAdapter;

    private StarIoExtManager mStarIoExtManager;

    private ListView mListView;
    private TextView mComment;

    private boolean mIsForeground;

    public AutoSwitchInterfaceExtFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(getActivity());

        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        mList    = new ArrayList<>();
        mAdapter = new ItemListAdapter(getActivity(), mList);

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        // Specifying AutoSwitch: for portName allows you to automatically select the interface for connecting to the printer.
        if (ModelCapability.canUseBarcodeReader(settings.getModelIndex())) {
            mStarIoExtManager = new StarIoExtManager(StarIoExtManager.Type.WithBarcodeReader, "AutoSwitch:", settings.getPortSettings(), 10000, getActivity());
        }
        else {
            mStarIoExtManager = new StarIoExtManager(StarIoExtManager.Type.Standard, "AutoSwitch:", settings.getPortSettings(), 10000, getActivity());
        }
        mStarIoExtManager.setCashDrawerOpenActiveHigh(settings.getCashDrawerOpenActiveHigh());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_auto_switch_interface_ext, container, false);

        mListView = rootView.findViewById(R.id.bcrTestListView);

        mListView.setAdapter(mAdapter);

        mComment = rootView.findViewById(R.id.bcrStatusTextView);

        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);

        mComment.startAnimation(animation);

        Button testButton = rootView.findViewById(R.id.bcrTestButton);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                print();
            }
        });

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new AlertDialog.Builder(Objects.requireNonNull(getActivity()))
                .setTitle("How to test AutoSwitch Interface function")
                .setMessage("Step 1: Perform Bluetooth pairing and also connect to LAN to take advantage of the AutoSwitch Interface feature.\n\n" +
                            "Step 2: Connect to the printer via USB and press the OK button.\n" +
                            "Step 3: Disconnect the USB cable. You can automatically connect to a printer via Bluetooth or LAN interface and monitoring printer.\n" +
                            "Step 4: Reconnect to the printer via USB or Bluetooth. Printer is automatically connected via USB or Bluetooth.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int index) {
                        mStarIoExtManager.setListener(mStarIoExtManagerListener);
                        mStarIoExtManager.connect(mConnectionCallback);

                        mProgressDialog.show();
                    }
                })
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();

        mIsForeground = true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.reloadIcon) {
            mAdapter.clear();

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
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mStarIoExtManager.disconnect(mConnectionCallback);
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

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            if (!result) {
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
            }
        }

        @Override
        public void onDisconnected() {
            // do nothing
        }
    };

    private void print() {
        mProgressDialog.show();

        PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
        PrinterSettings       settings       = settingManager.getPrinterSettings();

        Emulation emulation = ModelCapability.getEmulation(settings.getModelIndex());
        int paperSize = settings.getPaperSize();

        ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(PrinterSettingConstant.LANGUAGE_ENGLISH, paperSize);

        byte[] commands = PrinterFunctions.createRasterReceiptData(emulation, localizeReceipts, getResources());

        Communication.sendCommandsWithAutoInterfaceSelectExt(commands, mStarIoExtManager, 30000, mCallback);
    }

    private final Communication.AutoInterfaceSelectCallback mCallback = new Communication.AutoInterfaceSelectCallback() {
        @Override
        public void onStatus(String portName, Communication.CommunicationResult communicationResult) {
            if (!mIsForeground) {
                return;
            }

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance("CommResultDialog");
            dialog.setTitle("Communication Result");
            dialog.setMessage(Communication.getCommunicationResultMessage(communicationResult) + "\nPort Name: " + portName);
            dialog.setPositiveButton("OK");
            dialog.show(getChildFragmentManager());
        }
    };

    private void addItem(String text) {
        List<TextInfo> textList = new ArrayList<>();

        textList.add(new TextInfo(text, R.id.bcrValueTextView));
        mAdapter.add(new ItemList(R.layout.list_bcr_row, textList, Color.WHITE, true));

        if (mAdapter.getCount() > LIST_MAX_NUM) {
            mAdapter.remove(mAdapter.getItem(0));
        }

        int itemCount = mList.size();
        mListView.setSelection(itemCount - 1);
    }

    private final StarIoExtManagerListener mStarIoExtManagerListener = new StarIoExtManagerListener() {
        @Override
        public void onBarcodeDataReceive(byte[] barcodeData) {
            String[] barcodeDataArray = new String(barcodeData).split("\n");

            for(String data:barcodeDataArray) {
                addItem(data);
            }
        }

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
        public void onCashDrawerOpen() {
            mComment.setText("Cash Drawer Open.");

            mComment.setTextColor(Color.MAGENTA);
        }

        @Override
        public void onCashDrawerClose() {
            mComment.setText("Cash Drawer Close.");

            mComment.setTextColor(Color.BLUE);
        }

        @Override
        public void onBarcodeReaderImpossible() {
            mComment.setText("Barcode Reader Impossible.");

            mComment.setTextColor(Color.RED);
        }

        @Override
        public void onBarcodeReaderConnect() {
            mComment.setText("Barcode Reader Connect.");

            mComment.setTextColor(Color.BLUE);
        }

        @Override
        public void onBarcodeReaderDisconnect() {
            mComment.setText("Barcode Reader Disconnect.");

            mComment.setTextColor(Color.RED);
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
    };
}
