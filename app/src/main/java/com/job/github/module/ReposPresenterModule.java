package com.job.github.module;

import com.job.github.annotation.GitHubService;
import com.job.github.api.GitHubApi;
import com.job.github.model.ReposContractModel;
import com.job.github.model.ReposModel;
import com.job.github.presenter.ReposPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by slavik on 6/2/18.
 */

@Module(includes = {GitHubModule.class})
public class ReposPresenterModule {

    @Provides
    @Singleton
    ReposPresenter reposPresenter(ReposContractModel reposContractModel) {
        return new ReposPresenter(reposContractModel);
    }

    @Provides
    @Singleton
    ReposContractModel reposContractModel(@GitHubService GitHubApi gitHubApi) {
        return new ReposModel(gitHubApi);
    }
}
