package com.example.searchlol.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.searchlol.dataclass.SummonerClass;

@Database(entities = {SummonerClass.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    public abstract SummonerClassDao summonerClassDao();
    private static volatile AppDatabase INSTANCE;
    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "saved_summoner_db"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}