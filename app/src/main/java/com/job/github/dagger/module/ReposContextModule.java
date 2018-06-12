package com.job.github.dagger.module;

import android.content.Context;

import com.job.github.dagger.annotation.ReposFragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by slavik on 6/12/18.
 */

@Module
public class ReposContextModule {
    private Context context;

    public ReposContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @ReposFragmentScope
    Context context() {
        return context;
    }

}
