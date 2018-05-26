package com.job.github.api;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by slavik on 5/26/18.
 */

public class App extends Application {
    private static GitHubApi gitHubApi;

    @Override
    public void onCreate() {
        super.onCreate();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://github.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        gitHubApi = retrofit.create(GitHubApi.class);
    }

    public static GitHubApi getApi() {
        return gitHubApi;
    }
}
