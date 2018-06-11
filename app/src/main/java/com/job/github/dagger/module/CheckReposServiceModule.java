package com.job.github.dagger.module;

import android.content.Context;

import com.job.github.utils.NotificationHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by slavik on 6/11/18.
 */

@Module(includes = {ApplicationContextModule.class})
public class CheckReposServiceModule {

    @Singleton
    @Provides
    NotificationHelper notificationHelper(Context context) {
        return new NotificationHelper(context);
    }
}
