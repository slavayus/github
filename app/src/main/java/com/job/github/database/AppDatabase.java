package com.job.github.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.job.github.pojo.Repos;

/**
 * Created by slavik on 6/3/18.
 */

@Database(entities = {Repos.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ReposDao reposDao();
}
