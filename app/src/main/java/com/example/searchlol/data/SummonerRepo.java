package com.example.searchlol.data;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "repos")
public class SummonerRepo implements Serializable {
    @PrimaryKey
    @NonNull
    public String id;
}
