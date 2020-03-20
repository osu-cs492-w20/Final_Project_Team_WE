package com.example.searchlol.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import androidx.lifecycle.LiveData;

@Dao
public interface SummonerClassDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(SummonerClass name);

    @Delete
    void delete(SummonerClass name);

    @Query("SELECT * FROM summoners")
    LiveData<List<SummonerClass>> getAllSummoners();

    @Query("SELECT * FROM summoners WHERE name = :fullName LIMIT 1")
    LiveData<SummonerClass> getSummonerByName(String fullName);

}