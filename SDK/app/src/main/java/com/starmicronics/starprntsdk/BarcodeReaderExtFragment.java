package com.starmicronics.starprntsdk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

import com.starmicronics.starprntsdk.Communication.CommunicationResult;
import com.starmicronics.starprntsdk.Communication.Result;

import com.starmicronics.stario.StarResultCode;
import com.starmicronics.starioextension.ConnectionCallback;
import com.starmicronics.starioextension.StarIoExtManager;
import com.starmicronics.starioextension.StarIoExtManagerListener;

import java.util.ArrayList;
import java.util.List;

public class BarcodeReaderExtFragment extends CommonFragment implements CommonAlertDialogFragment.Callback {

    private static final String OPEN_FAILURE_DIALOG = "OpenFailureDialog";

    private static final int LIST_MAX_NUM = 30;

    private ProgressDialog mProgressDialog;

    private List<ItemList>  mList;
    private ItemListAdapter mAdapter;

    private ListView mListView;
    private TextView mComment;

    private StarIoExtManager mStarIoExtManager;

    private boolean mIsForeground;

    public BarcodeReaderExtFragment() {
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

        mStarIoExtManager = new StarIoExtManager(StarIoExtManager.Type.OnlyBarcodeReader, settings.getPortName(), settings.getPortSettings(), 10000, getActivity());     // 10000mS!!!
        mStarIoExtManager.setCashDrawerOpenActiveHigh(settings.getCashDrawerOpenActiveHigh());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_barcode_ext, container, false);

        mListView = rootView.findViewById(R.id.bcrTestListView);

        mListView.setAdapter(mAdapter);

        mComment = rootView.findViewById(R.id.bcrStatusTextView);

        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);

        mComment.startAnimation(animation);

        setHasOptionsMenu(true);

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
        public void onBarcodeDataReceive(byte[] data) {
            String[] barcodeDataArray = new String(data).split("\n");

            for(String barcodeData:barcodeDataArray) {
                addItem(barcodeData);
            }
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
