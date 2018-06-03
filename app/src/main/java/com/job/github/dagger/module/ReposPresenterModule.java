package com.job.github.dagger.module;

import com.job.github.api.GitHubApi;
import com.job.github.dagger.annotation.GitHubService;
import com.job.github.database.AppDatabase;
import com.job.github.mvp.model.ReposContractModel;
import com.job.github.mvp.model.ReposModel;
import com.job.github.mvp.presenter.ReposPresenter;
import com.job.github.utils.NetworkChecker;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by slavik on 6/2/18.
 */

@Module(includes = {GitHubModule.class, AppDatabaseModule.class, NetworkCheckerModule.class})
public class ReposPresenterModule {

    @Provides
    @Singleton
    ReposPresenter reposPresenter(ReposContractModel reposContractModel, NetworkChecker networkChecker) {
        return new ReposPresenter(reposContractModel, networkChecker);
    }

    @Provides
    @Singleton
    ReposContractModel reposContractModel(@GitHubService GitHubApi gitHubApi, AppDatabase appDatabase) {
        return new ReposModel(gitHubApi, appDatabase);
    }
}
