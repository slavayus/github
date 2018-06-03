package com.job.github.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.job.github.convertor.DateConverter;
import com.job.github.convertor.UrlConverter;
import com.job.github.pojo.Repos;
import com.job.github.pojo.User;

/**
 * Created by slavik on 6/3/18.
 */

@TypeConverters({DateConverter.class, UrlConverter.class})
@Database(entities = {Repos.class, User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ReposDao reposDao();

    public abstract UserDao userDao();
}
