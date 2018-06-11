package com.job.github.dagger.module;

import android.content.Context;

import com.job.github.utils.ClientPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by slavik on 6/11/18.
 */

@Module(includes = {ApplicationContextModule.class})
public class ClientPreferencesModule {

    @Singleton
    @Provides
    ClientPreferences clientPreferences(Context context) {
        return ClientPreferences.getInstance(context);
    }
}
