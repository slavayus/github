package com.job.github.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.job.github.api.pojo.Repos;
import com.job.github.api.pojo.User;
import com.job.github.database.convertor.DateConverter;
import com.job.github.database.convertor.UrlConverter;

/**
 * Created by slavik on 6/3/18.
 */

@TypeConverters({DateConverter.class, UrlConverter.class})
@Database(entities = {Repos.class, User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ReposDao reposDao();

    public abstract UserDao userDao();
}
