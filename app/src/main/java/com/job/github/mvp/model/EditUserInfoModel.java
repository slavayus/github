package com.job.github.mvp.model;

import com.job.github.api.GitHubApi;
import com.job.github.api.pojo.User;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditUserInfoModel implements EditUserInfoContractModel {
    private final GitHubApi gitHubApi;

    public EditUserInfoModel(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    public Single<User> updateUserInfo(User user, String token) {
        return gitHubApi
                .updateUserInfo(token, user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
