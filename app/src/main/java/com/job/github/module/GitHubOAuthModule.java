package com.job.github.module;

import com.job.github.annotation.GitHubOAuthService;
import com.job.github.annotation.OAuthRetrofit;
import com.job.github.api.GitHubApi;
import com.job.github.api.URLHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by slavik on 6/2/18.
 */

@Module(includes = {GsonFactoryModule.class})
public class GitHubOAuthModule {

    @Singleton
    @GitHubOAuthService
    @Provides
    GitHubApi gitHubApi(@OAuthRetrofit Retrofit retrofit) {
        return retrofit.create(GitHubApi.class);
    }

    @Singleton
    @OAuthRetrofit
    @Provides
    Retrofit retrofit(GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(URLHelper.REGISTER_BASE_URL)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

}
