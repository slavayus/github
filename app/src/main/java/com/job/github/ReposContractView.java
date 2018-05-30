package com.job.github;

import com.job.github.models.Repos;

import java.util.List;

public interface ReposContractView {
    void showRepos(List<Repos> data);

    String getUserLogin();

    String getClientId();

    String getClientSecret();

    void showLoadReposError();
}
