package com.job.github.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.job.github.api.GitHubApi;
import com.job.github.pojo.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeModel implements HomeContractModel {
    private final GitHubApi gitHubApi;

    public HomeModel(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @Override
    public void loadUser(String token, final OnLoadUser onLoadUser) {
        gitHubApi.getUser(token).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() == null) {
                    onLoadUser.onError();
                } else {
                    onLoadUser.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                onLoadUser.onError();
            }
        });

    }

    @Override
    public void loadAvatar(String avatarUrl, final OnDownloadAvatar onDownloadAvatar) {
        gitHubApi.loadAvatar(avatarUrl).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
                        onDownloadAvatar.onSuccess(bm);
                        return;
                    }
                }
                onDownloadAvatar.onError();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onDownloadAvatar.onError();
            }
        });
    }

    @Override
    public void updateUserInfo(User user, String token, UpdateUserInfo updateUserInfo) {
        gitHubApi.updateBio(token, user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        updateUserInfo.onSuccess(response.body());
                        return;
                    }
                }
                updateUserInfo.onError();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                updateUserInfo.onError();
            }
        });
    }
}
