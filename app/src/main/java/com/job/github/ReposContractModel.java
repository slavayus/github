package com.job.github;


import com.job.github.models.Repos;

import java.util.List;

public interface ReposContractModel {
    void loadRepos(String mUserLogin, String mClientId, String mClientSecret, OnLoadRepos onLoadRepos);

    interface OnLoadRepos {
        void onSuccess(List<Repos> data);

        void onError();
    }
}
