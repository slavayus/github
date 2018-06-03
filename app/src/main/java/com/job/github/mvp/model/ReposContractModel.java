package com.job.github.mvp.model;


import com.job.github.api.pojo.Repos;

import java.util.List;

public interface ReposContractModel {
    void loadRepos(String mUserLogin, String mClientId, String mClientSecret, boolean isThereNetwork, OnLoadRepos onLoadRepos);

    interface OnLoadRepos {
        void onSuccess(List<Repos> data);

        void onError();
    }
}
