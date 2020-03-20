package com.example.searchlol.utils;

import android.util.JsonReader;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.example.searchlol.utils.NetworkUtils;

import com.example.searchlol.data.ChampionInfo;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ChampionInfoUtil {
    public static final String url = "http://ddragon.leagueoflegends.com/cdn/10.6.1/data/en_US/champion.json";
    private static String json = null;
    static class ChampionList {
        Champion[] items;
    }

    static class Champion {
        public String id;
        public int key;
    }

    public static ArrayList<ChampionInfo> parseChampionInfo() {
        Gson gson = new Gson();
        ChampionList results = gson.fromJson(json, ChampionList.class);
        if (results != null) {
            ArrayList<ChampionInfo> championLists = new ArrayList<>();
            for (Champion champion : results.items) {
                ChampionInfo championInfo = new ChampionInfo();

                championInfo.id = champion.id;
                championInfo.key = champion.key;

                championLists.add(championInfo);
            }
            return championLists;
        } else {
            return  null;
        }
    }
}
