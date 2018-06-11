package com.job.github.utils;

import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.PersistableBundle;
import android.util.Log;

import com.job.github.api.GitHubApi;
import com.job.github.api.pojo.Repos;
import com.job.github.dagger.annotation.GitHubService;
import com.job.github.dagger.component.DaggerCheckReposServiceComponent;
import com.job.github.dagger.module.ApplicationContextModule;
import com.job.github.mvp.view.LaunchActivity;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by slavik on 6/11/18.
 */

public class CheckReposJobService extends JobService {
    private static final String TAG = "CheckReposJobService";
    private static final String CLIENT_SECRET = "CLIENT_SECRET";
    private static final String CLIENT_ID = "CLIENT_ID";
    @Inject NotificationHelper notification;
    @Inject
    @GitHubService
    GitHubApi gitHubApi;
    @Inject ClientPreferences clientPreferences;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: ");

        injectData();

        String clientId = params.getExtras().getString(CLIENT_ID);
        String clientSecret = params.getExtras().getString(CLIENT_SECRET);
        String userName = clientPreferences.getUserName();
        String lastRepo = clientPreferences.getLastRepo();

        gitHubApi.getRepos(userName, clientId, clientSecret).enqueue(new Callback<List<Repos>>() {
            @Override
            public void onResponse(Call<List<Repos>> call, Response<List<Repos>> response) {
                Log.d(TAG, "onResponse: ");
                if (lastRepo != null && response.body() != null && !lastRepo.equals(response.body().get(0).getName())) {
                    clientPreferences.setLastRepo(response.body().get(0).getName());
                    notifyUser(response.body().get(0).getName());
                }
                jobFinished(params, true);
            }

            @Override
            public void onFailure(Call<List<Repos>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
                jobFinished(params, true);
            }
        });

        return true;
    }

    private void injectData() {
        DaggerCheckReposServiceComponent
                .builder()
                .applicationContextModule(new ApplicationContextModule(this))
                .build()
                .injectReposService(this);
    }

    private void notifyUser(String name) {
        Intent intent = LaunchActivity.newInstance(this);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        notification.notify(1, notification.getNotificationRepos(pendingIntent, name));
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob: ");
        return true;
    }

    public static PersistableBundle creteExtras(String clientId, String clientSecret) {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putString(CLIENT_ID, clientId);
        persistableBundle.putString(CLIENT_SECRET, clientSecret);
        return persistableBundle;
    }
}
