package com.example.searchlol.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
import androidx.lifecycle.LiveData;

@Dao
public interface SummonerClassDao {
    @Insert
    void insert(SummonerClass summoner);

    @Delete
    void delete(SummonerClass summoner);

    @Query("SELECT * FROM summoner")
    LiveData<List<SummonerClass>> getAllSummoners();

    @Query("SELECT * FROM summoner WHERE name = :fullName LIMIT 1")
    LiveData<SummonerClass> getSummonerByName(String fullName);

}