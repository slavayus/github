package com.job.github.model;


import com.job.github.pojo.Repos;

import java.util.List;

public interface ReposContractModel {
    void loadRepos(String mUserLogin, String mClientId, String mClientSecret, boolean isThereNetwork, OnLoadRepos onLoadRepos);

    interface OnLoadRepos {
        void onSuccess(List<Repos> data);

        void onError();
    }
}
