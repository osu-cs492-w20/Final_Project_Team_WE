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
    @Insert
    void insert(SummonerRepo summoner);

    @Delete
    void delete(SummonerRepo summoner);

    @Query("SELECT * FROM repos")
    LiveData<List<SummonerRepo>> getAllSummoners();

    @Query("SELECT * FROM repos WHERE id = :sumId LIMIT 1")
    SummonerRepo getSummonerById(String sumId);
}