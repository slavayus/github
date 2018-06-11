package com.job.github.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.job.github.api.pojo.Repos;

import java.util.List;

/**
 * Created by slavik on 6/3/18.
 */

@Dao
public interface ReposDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Repos> repos);

    @Query("SELECT * FROM repos ORDER BY  pushedAt DESC")
    List<Repos> getAll();
}
