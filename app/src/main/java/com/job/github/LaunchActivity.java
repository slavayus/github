package com.job.github;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Properties;

public class LaunchActivity extends AppCompatActivity {
    private static final int START_APP = 0;
    private String clientId;
    private String clientSecret;
    private final LoadHandler mHandler = new LoadHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        long start = System.currentTimeMillis();

        loadClientDataFromAssets();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(START_APP);
            }
        }, Math.max(1000 - (start - System.currentTimeMillis()), 0));
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
                    activity.startActivity(WebViewActivity.newInstance(activity, activity.clientId, activity.clientSecret));
                    activity.finish();
                }
            }
        }
    }
}