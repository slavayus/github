package com.job.github.mvp.model;

import com.job.github.api.GitHubApi;
import com.job.github.api.pojo.Token;
import com.job.github.mvp.presenter.WebViewContractView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by slavik on 5/30/18.
 */

public class WebViewModel implements WebViewContractModel {

    private final GitHubApi gitHubApi;

    public WebViewModel(GitHubApi gitHubApi) {
        this.gitHubApi = gitHubApi;
    }

    @Override
    public void authenticate(String clientId, String clientSecret, String url, final WebViewContractView.OnAuthenticate onAuthenticate) {
        gitHubApi.getToken(clientId, clientSecret, url).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        onAuthenticate.onSuccess(response.body());
                        return;
                    }
                }
                onAuthenticate.onFailure();
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                onAuthenticate.onFailure();
            }
        });

    }
}
