package com.job.github.dagger.module;

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

@Module
public class FollowersFragmentModule {

    @Singleton
    @Provides
    FollowersFragmentPresenter followersFragmentPresenter(FollowersFragmentContractModel model) {
        return new FollowersFragmentPresenter(model);
    }

    @Singleton
    @Provides
    FollowersFragmentContractModel model() {
        return new FollowersFragmentModel();
    }


    @Provides
    @Singleton
    FollowersAdapter followersAdapter() {
        return new FollowersAdapter();
    }

}
