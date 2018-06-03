package com.job.github.module;

import android.content.Context;

import com.job.github.utils.NetworkChecker;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by slavik on 6/3/18.
 */

@Module(includes = {ApplicationContextModule.class})
public class NetworkCheckerModule {

    @Provides
    @Singleton
    NetworkChecker networkChecker(Context context) {
        return new NetworkChecker(context);
    }
}
