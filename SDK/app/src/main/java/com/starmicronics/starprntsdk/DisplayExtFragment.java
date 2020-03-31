package com.starmicronics.starprntsdk;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.starmicronics.starprntsdk.Communication.CommunicationResult;
import com.starmicronics.starprntsdk.Communication.Result;

import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;
import com.starmicronics.starioextension.IPeripheralConnectParser;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starprntsdk.functions.DisplayFunctions;
import com.starmicronics.starioextension.IDisplayCommandBuilder.InternationalType;
import com.starmicronics.starioextension.IDisplayCommandBuilder.CodePageType;
import com.starmicronics.starioextension.IDisplayCommandBuilder.CursorMode;
import com.starmicronics.starioextension.IDisplayCommandBuilder.ContrastMode;

import java.util.ArrayList;
import java.util.List;

public class DisplayExtFragment extends Fragment implements CommonAlertDialogFragment.Callback {
    private enum PeripheralStatus {
        Invalid,
        Impossible,
        Connect,
        Disconnect,
    }

    private ProgressDialog mProgressDialog;

    private TextView mComment;

    private List<ItemList>  mMainList;
    private ItemListAdapter mMainAdapter;
    private ListView        mMainListView;

    private List<ItemList>  mSubList;
    private ItemListAdapter mSubAdapter;
    private ListView        mSubListView;

    private static final int LIST_MAX_NUM = 30;

    private boolean mIsForeground;

    private PeripheralStatus mDisplayStatus = PeripheralStatus.Invalid;

    private boolean            mTryConnect = false;
    private DisplayWatchThread mThread = null;

    private InternationalType mInternationalType = InternationalType.USA;
    private CodePageType      mCodePageType      = CodePageType.CP437;

    private int mMainIndex;
    private int mSubIndex;

    public DisplayExtFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mProgressDialog = new ProgressDialog(getActivity());

        mProgressDialog.setMessage("Communicating...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);

        mMainList    = new ArrayList<>();
        mSubList     = new ArrayList<>();
        mMainAdapter = new ItemListAdapter(getActivity(), mMainList);
        mSubAdapter  = new ItemListAdapter(getActivity(), mSubList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_display_ext, container, false);

        mComment = rootView.findViewById(R.id.StatusTextView);

        setHasOptionsMenu(true);

        mMainListView = rootView.findViewById(R.id.DisplayMainListView);

        mMainListView.setAdapter(mMainAdapter);
        mMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//              mSubList.removeAll();
                mMainIndex = position;

                mSubAdapter.clear();

                switch (position) {
                    default:
//                  case 0:
                    case 1:
                        addSubTitle("Text Sub Contents");
                        addSubItem("Pattern 1");
                        addSubItem("Pattern 2");
                        addSubItem("Pattern 3");
                        addSubItem("Pattern 4");
                        addSubItem("Pattern 5");
                        addSubItem("Pattern 6");
                        break;
                    case 2:
                        addSubTitle("Graphic Sub Contents");
                        addSubItem("Pattern 1");
                        addSubItem("Pattern 2");
                        break;
                    case 3:
                        addSubTitle("Turn On/Off Sub Contents");
                        addSubItem("Turn On");
                        addSubItem("Turn Off");
                        break;
                    case 4:
                        addSubTitle("Cursor Sub Contents");
                        addSubItem("Off");
                        addSubItem("Blink");
                        addSubItem("On");
                        break;
                    case 5:
                        addSubTitle("Contrast Sub Contents");
                        addSubItem("Contrast -3");
                        addSubItem("Contrast -2");
                        addSubItem("Contrast -1");
                        addSubItem("Default");
                        addSubItem("Contrast +1");
                        addSubItem("Contrast +2");
                        addSubItem("Contrast +3");
                        break;
                    case 6:
                        addSubTitle("Character International Sub Contents");
                        addSubItem("USA");
                        addSubItem("France");
                        addSubItem("Germany");
                        addSubItem("UK");
                        addSubItem("Denmark");
                        addSubItem("Sweden");
                        addSubItem("Italy");
                        addSubItem("Spain");
                        addSubItem("Japan");
                        addSubItem("Norway");
                        addSubItem("Denmark 2");
                        addSubItem("Spain 2");
                        addSubItem("Latin America");
                        addSubItem("Korea");
                        break;
                    case 7:
                        addSubTitle("Character Code Page Sub Contents");
                        addSubItem("Code Page 437");
                        addSubItem("Katakana");
                        addSubItem("Code Page 850");
                        addSubItem("Code Page 860");
                        addSubItem("Code Page 863");
                        addSubItem("Code Page 865");
                        addSubItem("Code Page 1252");
                        addSubItem("Code Page 866");
                        addSubItem("Code Page 852");
                        addSubItem("Code Page 858");
                        addSubItem("Japanese");
                        addSubItem("Simplified Chinese");
                        addSubItem("Traditional Chinese");
                        addSubItem("Hangul");
                        break;
                    case 8:
                        addSubTitle("User Defined Character Sub Contents");
                        addSubItem("Set");
                        addSubItem("Clear");
                        break;
                }

                mSubListView.setSelectionFromTop(0, 0);
            }
        });

        mSubListView = rootView.findViewById(R.id.DisplaySubListView);

        mSubListView.setAdapter(mSubAdapter);
        mSubListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSubIndex = position;

                if (mDisplayStatus == PeripheralStatus.Connect) {
                    mProgressDialog.show();

                    byte[] commandData;
                    int    index = mSubIndex - 1;

                    switch (mMainIndex) {
                        default:
                        case 1:
                            commandData = DisplayFunctions.createTextPattern(index);

                            break;
                        case 2:
                            commandData = DisplayFunctions.createGraphicPattern(index, getResources());

                            break;
                        case 3:
                            boolean isTurnOn = (index == 0);

                            commandData = DisplayFunctions.createTurnOn(isTurnOn);
                            break;
                        case 4:
                            CursorMode cursorMode;

                            switch (index) {
                                default:
                                case 0:     cursorMode = CursorMode.Off;   break;
                                case 1:     cursorMode = CursorMode.Blink; break;
                                case 2:     cursorMode = CursorMode.On;    break;
                            }

                            commandData = DisplayFunctions.createCursorMode(cursorMode);
                            break;
                        case 5:
                            ContrastMode contrastMode;

                            switch (index) {
                                default:
                                case 0:     contrastMode = ContrastMode.Minus3;    break;
                                case 1:     contrastMode = ContrastMode.Minus2;    break;
                                case 2:     contrastMode = ContrastMode.Minus1;    break;
                                case 3:     contrastMode = ContrastMode.Default;   break;
                                case 4:     contrastMode = ContrastMode.Plus1;     break;
                                case 5:     contrastMode = ContrastMode.Plus2;     break;
                                case 6:     contrastMode = ContrastMode.Plus3;     break;
                            }

                            commandData = DisplayFunctions.createContrastMode(contrastMode);
                            break;
                        case 6:
                            switch (index) {
                                default:
                                case 0:     mInternationalType = InternationalType.USA;            break;
                                case 1:     mInternationalType = InternationalType.France;         break;
                                case 2:     mInternationalType = InternationalType.Germany;        break;
                                case 3:     mInternationalType = InternationalType.UK;             break;
                                case 4:     mInternationalType = InternationalType.Denmark;        break;
                                case 5:     mInternationalType = InternationalType.Sweden;         break;
                                case 6:     mInternationalType = InternationalType.Italy;          break;
                                case 7:     mInternationalType = InternationalType.Spain;          break;
                                case 8:     mInternationalType = InternationalType.Japan;          break;
                                case 9:     mInternationalType = InternationalType.Norway;         break;
                                case 10:    mInternationalType = InternationalType.Denmark2;       break;
                                case 11:    mInternationalType = InternationalType.Spain2;         break;
                                case 12:    mInternationalType = InternationalType.LatinAmerica;   break;
                                case 13:    mInternationalType = InternationalType.Korea;          break;
                            }

                            commandData = DisplayFunctions.createCharacterSet(mInternationalType, mCodePageType);
                            break;
                        case 7:
                            switch (index) {
                                default:
                                case 0:     mCodePageType = CodePageType.CP437;                break;
                                case 1:     mCodePageType = CodePageType.Katakana;             break;
                                case 2:     mCodePageType = CodePageType.CP850;                break;
                                case 3:     mCodePageType = CodePageType.CP860;                break;
                                case 4:     mCodePageType = CodePageType.CP863;                break;
                                case 5:     mCodePageType = CodePageType.CP865;                break;
                                case 6:     mCodePageType = CodePageType.CP1252;               break;
                                case 7:     mCodePageType = CodePageType.CP866;                break;
                                case 8:     mCodePageType = CodePageType.CP852;                break;
                                case 9:     mCodePageType = CodePageType.CP858;                break;
                                case 10:    mCodePageType = CodePageType.Japanese;             break;
                                case 11:    mCodePageType = CodePageType.SimplifiedChinese;    break;
                                case 12:    mCodePageType = CodePageType.TraditionalChinese;   break;
                                case 13:    mCodePageType = CodePageType.Hangul;               break;
                            }

                            commandData = DisplayFunctions.createCharacterSet(mInternationalType, mCodePageType);
                            break;
                        case 8:
                            boolean isSet = (index == 0);

                            commandData = DisplayFunctions.createUserDefinedCharacter(isSet);
                            break;
                    }

                    Communication.sendCommandsDoNotCheckCondition(DisplayExtFragment.class, commandData, mThread.getPort(), mCallback);
                }
                else {
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                    }

                    CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance("CommResultDialog");
                    dialog.setTitle("Communication Result");
                    dialog.setMessage("Display Disconnect");
                    dialog.setPositiveButton("OK");
                    dialog.show(getChildFragmentManager());
                }
            }
        });

        addMainTitle("Contents");
        addMainItem("Text");
        addMainItem("Graphic");
        addMainItem("Back Light (Turn On / Off)");
        addMainItem("Cursor");
        addMainItem("Contrast");
        addMainItem("Character International");
        addMainItem("Character Code Page");
        addMainItem("User Defined Character");

        mMainListView.setSelectionFromTop(0, 0);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        mIsForeground = true;

        connect();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.reloadIcon) {

            disconnect();
            connect();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();

        mIsForeground = false;

        if (mProgressDialog != null && !mTryConnect) {
            mProgressDialog.dismiss();
        }

        disconnect();
    }

    @Override
    public void onDialogResult(String tag, Intent data) {
        // Do nothing
    }

    private void connect() {
        AsyncTask<Void, Void, StarIOPort> task = new AsyncTask<Void, Void, StarIOPort>() {
            @Override
            protected void onPreExecute() {
                mTryConnect = true;
                mProgressDialog.show();
            }

            @Override
            protected StarIOPort doInBackground(Void... voids) {
                if (mThread != null) {
                    if (mThread.getWaitDisconnect()) {
                        try {
                            mThread.join();
                            mThread = null;
                        } catch (InterruptedException e) {
                            // Do nothing
                        }
                    }
                    else {
                        return null;
                    }
                }

                StarIOPort port = null;

                PrinterSettingManager settingManager = new PrinterSettingManager(getActivity());
                PrinterSettings       settings       = settingManager.getPrinterSettings();

                try {
                    synchronized (DisplayExtFragment.class) {
                        port = StarIOPort.getPort(settings.getPortName(), settings.getPortSettings(), 10000, getActivity());
                    }
                } catch (StarIOPortException e) {
                    // Do Nothing
                }
                return port;
            }

            @Override
            protected void onPostExecute(StarIOPort port) {
                mTryConnect = false;

                if (!mIsForeground) {
                    mThread = new DisplayWatchThread(port);
                    mThread.disconnect();
                    mThread.start();
                    return;
                }

                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }

                if (port != null) {
                    mThread = new DisplayWatchThread(port);
                    mThread.start();

                    mDisplayStatus = PeripheralStatus.Invalid;
                    mComment.clearAnimation();
                    mComment.setText("");

                }
                else {
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
                    mComment.startAnimation(animation);
                    mComment.setTextColor(Color.RED);
                    mComment.setText("Check the device. (Power and Bluetooth pairing)\nThen touch up the Refresh button.");

                    CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance("CommResultDialog");
                    dialog.setTitle("Communication Result");
                    dialog.setMessage("Fail to openPort");
                    dialog.setPositiveButton("OK");
                    dialog.show(getChildFragmentManager());
                }
            }
        };

        if (!mTryConnect) {
            task.execute();
        }
    }

    void disconnect() {
        if (mThread != null) {
            mThread.disconnect();
        }
    }

    final Communication.SendCallback mCallback = new Communication.SendCallback() {
        @Override
        public void onStatus(CommunicationResult communicationResult) {
            if (!mIsForeground) {
                return;
            }

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }

            if (communicationResult.getResult() != Result.Success) {
                CommonAlertDialogFragment dialog = CommonAlertDialogFragment.newInstance("CommResultDialog");
                dialog.setTitle("Communication Result");
                dialog.setMessage(Communication.getCommunicationResultMessage(communicationResult));
                dialog.setPositiveButton("OK");
                dialog.show(getChildFragmentManager());
            }
        }
    };

    protected void addMainTitle(String title) {
        List<TextInfo> textList = new ArrayList<>();

        textList.add(new TextInfo(title, R.id.menuTextView));

        mMainAdapter.add(new ItemList(R.layout.list_main_title_row, textList, false));
    }

    protected void addSubTitle(String title) {
        List<TextInfo> textList = new ArrayList<>();

        textList.add(new TextInfo(title, R.id.menuTextView));

        mSubAdapter.add(new ItemList(R.layout.list_main_title_row, textList, false));
    }

    private void addMainItem(String text) {
        List<TextInfo> textList = new ArrayList<>();

        textList.add(new TextInfo(text, R.id.bcrValueTextView));
        mMainAdapter.add(new ItemList(R.layout.list_bcr_row, textList, true));

        if (mMainAdapter.getCount() > LIST_MAX_NUM) {
            mMainAdapter.remove(mMainAdapter.getItem(0));
        }

        int itemCount = mMainList.size();
        mMainListView.setSelection(itemCount - 1);
    }

    private void addSubItem(String text) {
        List<TextInfo> textList = new ArrayList<>();

        textList.add(new TextInfo(text, R.id.bcrValueTextView));
        mSubAdapter.add(new ItemList(R.layout.list_bcr_row, textList, true));

        if (mSubAdapter.getCount() > LIST_MAX_NUM) {
            mSubAdapter.remove(mSubAdapter.getItem(0));
        }

        int itemCount = mSubList.size();
        mSubListView.setSelection(itemCount - 1);
    }

    private class DisplayWatchThread extends Thread {
        private boolean    mIsStop       = false;
        private StarIOPort mPort         = null;
        private boolean    mWaitCallback = false;
        private boolean    mReleasePort  = false;

        DisplayWatchThread(StarIOPort port) {
            mPort = port;
        }

        void disconnect(){
            mIsStop = true;
        }

        boolean getWaitDisconnect() {
            return mIsStop;
        }

        StarIOPort getPort() {
            return mPort;
        }

        @Override
        public void run() {

            while(!mIsStop) {
                if (mPort != null) {
                    mWaitCallback = true;

                    final IPeripheralConnectParser parser = StarIoExt.createDisplayConnectParser(StarIoExt.DisplayModel.SCD222);

                    Communication.parseDoNotCheckCondition(DisplayExtFragment.class, parser, mPort, new Communication.SendCallback() {
                        @Override
                        public void onStatus(CommunicationResult communicationResult) {
                            mWaitCallback = false;

                            if (!mIsForeground) {
                                return;
                            }

                            if (communicationResult.getResult() == Result.Success) {
                                if (parser.isConnected()) {
                                    if (mDisplayStatus != PeripheralStatus.Connect) {
                                        mComment.clearAnimation();
                                        mComment.setText("");

                                        mDisplayStatus = PeripheralStatus.Connect;
                                    }
                                }
                                else {
                                    if (mDisplayStatus != PeripheralStatus.Disconnect) {
                                        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
                                        mComment.startAnimation(animation);
                                        mComment.setTextColor(Color.RED);
                                        mComment.setText("Display Disconnect");

                                        mDisplayStatus = PeripheralStatus.Disconnect;
                                    }
                                }
                            }
                            else {
                                if (mDisplayStatus != PeripheralStatus.Impossible) {
                                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.blink);
                                    mComment.startAnimation(animation);
                                    mComment.setTextColor(Color.RED);
                                    mComment.setText("Printer Impossible");

                                    mDisplayStatus = PeripheralStatus.Impossible;
                                }
                            }
                        }
                    });

                    while (mWaitCallback) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            // Do nothing
                        }
                    }

                    if (mReleasePort) {
                        break;
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // Do nothing
                }
            }

            if (mPort != null) {
                try {
                    synchronized (DisplayExtFragment.class) {
                        mDisplayStatus = PeripheralStatus.Invalid;
                        StarIOPort.releasePort(mPort);
                        mPort = null;
                    }
                } catch (StarIOPortException e) {
                    // Do nothing
                }
            }
        }
    }
}
