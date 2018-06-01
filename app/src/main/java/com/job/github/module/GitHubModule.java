package com.job.github.module;

import com.job.github.annotation.ApiRetrofit;
import com.job.github.annotation.GitHubService;
import com.job.github.api.GitHubApi;
import com.job.github.api.URLHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by slavik on 6/1/18.
 */

@Module(includes = {GsonFactoryModule.class})
public class GitHubModule {

    @Singleton
    @GitHubService
    @Provides
    GitHubApi gitHubApi(@ApiRetrofit Retrofit retrofit) {
        return retrofit.create(GitHubApi.class);
    }

    @Singleton
    @ApiRetrofit
    @Provides
    Retrofit retrofit(GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(URLHelper.API_BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }
}
