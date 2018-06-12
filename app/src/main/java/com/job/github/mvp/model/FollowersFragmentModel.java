package com.job.github.mvp.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.job.github.api.GitHubApi;
import com.job.github.api.pojo.User;

import java.util.List;

import okhttp3.ResponseBody;
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
    public void downloadAllFollowers(String userLogin, String clientId, String clientSecret, OnDownloadFollowers onDownloadFollowersFollowers) {

        api.getFollowers(userLogin, clientId, clientSecret).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        onDownloadFollowersFollowers.onSuccess(response.body());
                        Log.d(TAG, "onResponse: download followers success");
                        return;
                    }
                }
                Log.d(TAG, "onResponse: download followers error");
                onDownloadFollowersFollowers.onError();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d(TAG, "onFailure: download followers " + t.getMessage());
                onDownloadFollowersFollowers.onError();
            }
        });

    }

    @Override
    public void downloadUser(String login, String clientId, String clientSecret, OnDownloadUser onDownloadUser) {
        api.getUserByLogin(login, clientId, clientSecret).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        onDownloadUser.onSuccess(response.body());
                        Log.d(TAG, "onResponse: download user success");
                        return;
                    }
                }
                Log.d(TAG, "onResponse: download user error");
                onDownloadUser.onError();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, "onFailure: download user " + t.getMessage());
                onDownloadUser.onError();
            }
        });
    }

    @Override
    public void downloadUserAvatar(String url, OnDownloadUserAvatar onDownloadUserAvatar) {
        api.loadAvatar(url).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
                        onDownloadUserAvatar.onSuccess(bm);
                        Log.d(TAG, "onResponse: download avatar success");
                        return;
                    }
                }
                Log.d(TAG, "onResponse: download avatar error");
                onDownloadUserAvatar.onError();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: download avatar " + t.getMessage());
                onDownloadUserAvatar.onError();
            }
        });
    }
}
