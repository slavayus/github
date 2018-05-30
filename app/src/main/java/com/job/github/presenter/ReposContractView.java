package com.job.github.presenter;

import com.job.github.pojo.Repos;

import java.util.List;

public interface ReposContractView {
    void showRepos(List<Repos> data);

    String getUserLogin();

    String getClientId();

    String getClientSecret();

    void showLoadReposError();
}
