package com.job.github.mvp.model;

import com.job.github.api.pojo.User;

import io.reactivex.Single;

public interface EditUserInfoContractModel {
    Single<User> updateUserInfo(User user, String token);
}
