package com.job.github.mvp.model;

import android.util.Log;

import com.job.github.api.GitHubApi;
import com.job.github.api.pojo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by slavik on 6/11/18.
 */

public class FollowersFragmentModel implements FollowersFragmentContractModel {
    private static final String TAG = "FollowersFragmentModel";
    private final GitHubApi api;

    public FollowersFragmentModel(GitHubApi api) {
        this.api = api;
    }

    @Override
    public void downloadAllFollowers(String userLogin, OnDownloadFollowers onDownloadFollowers) {

        api.getFollowers(userLogin).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        onDownloadFollowers.onSuccess(response.body());
                        Log.d(TAG, "onResponse: success");
                        return;
                    }
                }
                Log.d(TAG, "onResponse: error");
                onDownloadFollowers.onError();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                onDownloadFollowers.onError();
            }
        });

    }
}
