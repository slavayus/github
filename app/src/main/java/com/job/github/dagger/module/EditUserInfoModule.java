package com.job.github.dagger.module;

import com.job.github.api.GitHubApi;
import com.job.github.dagger.annotation.GitHubService;
import com.job.github.mvp.model.EditUserInfoContractModel;
import com.job.github.mvp.model.EditUserInfoModel;
import com.job.github.mvp.presenter.EditUserInfoPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by slavik on 6/7/18.
 */

@Module(includes = {GitHubModule.class})
public class EditUserInfoModule {
    @Singleton
    @Provides
    EditUserInfoPresenter editUserInfoPresenter(EditUserInfoContractModel model) {
        return new EditUserInfoPresenter(model);
    }

    @Singleton
    @Provides
    EditUserInfoContractModel editUserInfoContractModel(@GitHubService GitHubApi gitHubApi) {
        return new EditUserInfoModel(gitHubApi);
    }
}
