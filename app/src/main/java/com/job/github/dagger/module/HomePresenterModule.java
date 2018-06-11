package com.job.github.dagger.module;

import com.job.github.api.GitHubApi;
import com.job.github.dagger.annotation.GitHubService;
import com.job.github.database.AppDatabase;
import com.job.github.mvp.model.HomeContractModel;
import com.job.github.mvp.model.HomeModel;
import com.job.github.mvp.presenter.HomePresenter;
import com.job.github.utils.ClientPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by slavik on 6/2/18.
 */

@Module(includes = {GitHubModule.class, AppDatabaseModule.class, ClientPreferencesModule.class})
public class HomePresenterModule {

    @Provides
    @Singleton
    HomePresenter homePresenter(HomeContractModel homeContractModel, ClientPreferences clientPreferences) {
        return new HomePresenter(homeContractModel, clientPreferences);
    }

    @Provides
    @Singleton
    HomeContractModel homeModel(@GitHubService GitHubApi gitHubApi, AppDatabase database) {
        return new HomeModel(gitHubApi, database);
    }

}
