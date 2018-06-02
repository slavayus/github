package com.job.github.model;

import com.job.github.api.GitHubApi;
import com.job.github.pojo.Repos;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReposModel implements ReposContractModel {

    private final GitHubApi gitHubApi;

    public ReposModel(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @Override
    public void loadRepos(String mUserLogin, String mClientId, String mClientSecret, final OnLoadRepos onLoadRepos) {
        gitHubApi.getRepos(mUserLogin, mClientId, mClientSecret).enqueue(new Callback<List<Repos>>() {
            @Override
            public void onResponse(Call<List<Repos>> call, Response<List<Repos>> response) {
                if (response.body() != null) {
                    onLoadRepos.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Repos>> call, Throwable t) {
                onLoadRepos.onError();
            }
        });
    }
}
