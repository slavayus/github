package com.job.github.mvp.presenter;

import com.job.github.api.pojo.Repos;

import java.util.List;

public interface ReposContractView {
    void showRepos(List<Repos> data);

    String getUserLogin();

    String getClientId();

    String getClientSecret();

    void showLoadReposError();

    void showLoaderFragment();

    void stopLoaderFragment();
}
