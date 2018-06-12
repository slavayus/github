package com.job.github.dagger.module;

import android.content.Context;

import com.job.github.dagger.annotation.ReposFragmentScope;
import com.job.github.mvp.view.adapter.ReposAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by slavik on 6/2/18.
 */

@Module(includes = {ReposContextModule.class})
public class ReposFragmentModule {

    @Provides
    @ReposFragmentScope
    ReposAdapter reposAdapter(Context context) {
        return new ReposAdapter(context);
    }
}
