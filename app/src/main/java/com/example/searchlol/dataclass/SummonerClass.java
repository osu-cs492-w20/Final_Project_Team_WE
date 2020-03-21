package com.example.searchlol.dataclass;

import org.jetbrains.annotations.NotNull;
import java.io.Serializable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "summoners")
public class SummonerClass implements Serializable {
    @PrimaryKey
    @NonNull
    public String name;

    public String id;
    public String summonerLevel;
    public String accountId;
    //public String puuid;

    public int profileIconId;
    public long revisionDate;



}
