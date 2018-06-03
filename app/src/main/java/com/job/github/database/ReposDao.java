package com.job.github.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.job.github.pojo.Repos;

import java.util.List;

/**
 * Created by slavik on 6/3/18.
 */

@Dao
public interface ReposDao {
    @Insert
    void insertAll(List<Repos> repos);

    @Insert
    void insertOne(Repos repos);
}
