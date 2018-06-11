package com.job.github.utils;

import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.job.github.mvp.view.LaunchActivity;

/**
 * Created by slavik on 6/11/18.
 */

public class CheckReposJobService extends JobService {
    private static final String TAG = "CheckReposJobService";
    private final NotificationHelper notification;

    public CheckReposJobService() {
        this.notification = new NotificationHelper(this);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: ");
        Context context = getApplicationContext();
        Intent intent = LaunchActivity.newInstance(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        new Thread(() -> {
            try {
                Thread.sleep(1000);

                notification.notify(1, notification.getNotificationRepos(pendingIntent));

                jobFinished(params, true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob: ");
        return true;
    }
}
