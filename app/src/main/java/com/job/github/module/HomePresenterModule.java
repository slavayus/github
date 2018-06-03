package com.job.github.module;

import com.job.github.annotation.GitHubService;
import com.job.github.api.GitHubApi;
import com.job.github.database.AppDatabase;
import com.job.github.model.HomeContractModel;
import com.job.github.model.HomeModel;
import com.job.github.presenter.HomePresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by slavik on 6/2/18.
 */

@Module(includes = {GitHubModule.class, AppDatabaseModule.class})
public class HomePresenterModule {

    @Provides
    @Singleton
    HomePresenter homePresenter(HomeContractModel homeContractModel) {
        return new HomePresenter(homeContractModel);
    }

    @Provides
    @Singleton
    HomeContractModel homeModel(@GitHubService GitHubApi gitHubApi, AppDatabase database) {
        return new HomeModel(gitHubApi, database);
    }

}
