package com.starmicronics.starprntsdk;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class UsbAttachedActivity extends AppCompatActivity {
    private static final String BUNDLE_KEY_SAVED_INSTANCE    = "bundle_key_saved_instance";

    private boolean mIsExecuted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null) {
            mIsExecuted = savedInstanceState.containsKey(BUNDLE_KEY_SAVED_INSTANCE);
        }

        if (!mIsExecuted) {
            ActivityManager activityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.AppTask> tasks = activityManager.getAppTasks();
            ActivityManager.RecentTaskInfo taskInfo = tasks.get(0).getTaskInfo();
            Intent baseIntent = taskInfo.baseIntent;

            if (baseIntent.getComponent().getClassName().equals(this.getComponentName().getClassName())) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                startActivity(intent);
                mIsExecuted = true;
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(BUNDLE_KEY_SAVED_INSTANCE, mIsExecuted);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mIsExecuted) {
            finish();
        }
    }
}
