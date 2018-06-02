package com.job.github.module;

import com.job.github.adapter.ReposAdapter;
import com.job.github.annotation.ReposFragmentScope;

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
