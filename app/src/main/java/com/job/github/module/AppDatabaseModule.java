package com.job.github.module;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.job.github.database.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by slavik on 6/3/18.
 */

@Module(includes = {ApplicationContextModule.class})
public class AppDatabaseModule {

    @Provides
    @Singleton
    AppDatabase appDatabase(Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class, "github").build();
    }
}
