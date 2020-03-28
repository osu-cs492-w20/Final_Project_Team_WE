package com.example.searchlol.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
import androidx.lifecycle.LiveData;

import com.example.searchlol.dataclass.SummonerClass;

@Dao
public interface SummonerClassDao {
    @Insert
    void insert(SummonerClass summoner);

    @Delete
    void delete(SummonerClass summoner);

    @Query("SELECT * FROM summoners")
    LiveData<List<SummonerClass>> getAllSummoners();

    @Query("SELECT * FROM summoners WHERE id = :sumId LIMIT 1")
    SummonerClass getSummonerById(String sumId);
}