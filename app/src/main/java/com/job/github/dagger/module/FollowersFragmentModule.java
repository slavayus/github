package com.job.github.dagger.module;

import com.job.github.api.GitHubApi;
import com.job.github.dagger.annotation.GitHubService;
import com.job.github.mvp.model.FollowersFragmentContractModel;
import com.job.github.mvp.model.FollowersFragmentModel;
import com.job.github.mvp.presenter.FollowersFragmentPresenter;
import com.job.github.mvp.view.adapter.FollowersAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by slavik on 6/11/18.
 */

@Module(includes = {GitHubModule.class})
public class FollowersFragmentModule {

    @Singleton
    @Provides
    FollowersFragmentPresenter followersFragmentPresenter(FollowersFragmentContractModel model) {
        return new FollowersFragmentPresenter(model);
    }

    @Singleton
    @Provides
    FollowersFragmentContractModel model(@GitHubService GitHubApi api) {
        return new FollowersFragmentModel(api);
    }


    @Provides
    @Singleton
    FollowersAdapter.OnItemClickListener onItemClickListener(FollowersFragmentPresenter followersFragmentPresenter) {
        return followersFragmentPresenter::itemClicked;
    }

    @Provides
    @Singleton
    FollowersAdapter followersAdapter(FollowersAdapter.OnItemClickListener listener) {
        return new FollowersAdapter(listener);
    }

}
