package com.job.github.mvp.model;

import android.util.Log;

import com.job.github.api.GitHubApi;
import com.job.github.api.pojo.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by slavik on 6/7/18.
 */

public class EditUserInfoModel implements EditUserInfoContractModel {
    private static final String TAG = "EditUserInfoModel";
    private final GitHubApi gitHubApi;

    public EditUserInfoModel(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @Override
    public void updateUserInfo(User user, String token, OnUpdateUserInfo onUpdateUserInfo) {
        gitHubApi.updateUserInfo(token, user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        onUpdateUserInfo.onSuccess(response.body());
                        return;
                    }
                }
                onUpdateUserInfo.onError();
                Log.d(TAG, "onResponse: " + response.code());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                onUpdateUserInfo.onError();
            }
        });
    }
}
