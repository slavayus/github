package com.job.github.mvp.view;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.job.github.R;
import com.job.github.utils.CheckReposJobService;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class LaunchActivity extends AppCompatActivity {
    private static final int START_APP = 0;
    private static final int LOAD_ARTWORK_JOB_ID = 1;
    private String clientId;
    private String clientSecret;
    private final LoadHandler mHandler = new LoadHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        long start = System.currentTimeMillis();

        loadClientDataFromAssets();

        mHandler.postDelayed(() -> mHandler.sendEmptyMessage(START_APP), Math.max(1000 - (start - System.currentTimeMillis()), 0));

        ComponentName jobService = new ComponentName(this, CheckReposJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(LOAD_ARTWORK_JOB_ID, jobService)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresCharging(false)
                .setRequiresDeviceIdle(false)
                .setBackoffCriteria(TimeUnit.SECONDS.toMillis(5), JobInfo.BACKOFF_POLICY_LINEAR);

        JobScheduler jobScheduler =
                (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        if (jobScheduler != null) {
            jobScheduler.schedule(builder.build());
        }
    }

    private void loadClientDataFromAssets() {
        Properties properties = new Properties();
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("client.properties");
            properties.load(inputStream);
            clientId = properties.getProperty("clientId");
            clientSecret = properties.getProperty("clientSecret");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Intent newInstance(Context context) {
        return new Intent(context, LaunchActivity.class);
    }

    private static class LoadHandler extends Handler {
        private final WeakReference<LaunchActivity> mActivity;

        LoadHandler(LaunchActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LaunchActivity activity = mActivity.get();
            if (activity != null) {
                if (msg.what == START_APP) {
                    activity.startActivity(MainActivity.newInstance(activity, activity.clientId, activity.clientSecret));
                    activity.finish();
                }
            }
        }
    }
}