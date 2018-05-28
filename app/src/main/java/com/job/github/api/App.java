package com.job.github.api;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.job.github.URLHelper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by slavik on 5/26/18.
 */

public class App extends Application {
    private static GitHubApi gitHubRegisterApi;
    private static GitHubApi gitHubApi;

    @Override
    public void onCreate() {
        super.onCreate();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLHelper.REGISTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        gitHubRegisterApi = retrofit.create(GitHubApi.class);

        retrofit = new Retrofit.Builder()
                .baseUrl(URLHelper.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        gitHubApi = retrofit.create(GitHubApi.class);
    }

    public static GitHubApi getRegisterApi() {
        return gitHubRegisterApi;
    }

    public static GitHubApi getGitHubApi() {
        return gitHubApi;
    }
}
