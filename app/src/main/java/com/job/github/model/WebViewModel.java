package com.job.github.model;

import com.job.github.component.DaggerGitHubApiComponent;
import com.job.github.pojo.Token;
import com.job.github.presenter.WebViewContractView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by slavik on 5/30/18.
 */

public class WebViewModel implements WebViewContractModel {

    @Override
    public void authenticate(String clientId, String clientSecret, String url, final WebViewContractView.OnAuthenticate onAuthenticate) {
        DaggerGitHubApiComponent
                .create()
                .getGitHubRegisterService()
                .getToken(clientId, clientSecret, url).enqueue(new Callback<Token>() {
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
