package com.starmicronics.starprntsdk;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;
import java.util.TimerTask;

public class TitleFragment extends Fragment {
    Timer mTimer;

    public TitleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_title, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        final Handler handler = new Handler();

        TimerTask task = new TimerTask(){
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        startMainActivity();
                    }
                });
            }
        };

        mTimer = new Timer();
        mTimer.schedule(task, 1500);
    }

    @Override
    public void onPause() {
        super.onPause();
        mTimer.cancel();
    }

    private void startMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, getString(R.string.app_name) + " Ver." + getVersionName(getActivity()));
        intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_INFO_BUTTON, true);

        startActivity(intent);
    }

    private static String getVersionName(Context context) {
        String versionName = "";

        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        }
        catch (PackageManager.NameNotFoundException e) {
            // do nothing
        }

        return versionName;
    }
}
