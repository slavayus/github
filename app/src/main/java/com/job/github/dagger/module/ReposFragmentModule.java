package com.job.github.dagger.module;

import com.job.github.dagger.annotation.ReposFragmentScope;
import com.job.github.mvp.view.adapter.ReposAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by slavik on 6/2/18.
 */

@Module
public class ReposFragmentModule {

    @Provides
    @ReposFragmentScope
    ReposAdapter reposAdapter() {
        return new ReposAdapter();
    }
}
