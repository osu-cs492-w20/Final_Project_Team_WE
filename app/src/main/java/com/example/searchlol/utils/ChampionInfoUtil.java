package com.example.searchlol.utils;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;

import com.example.searchlol.data.ChampionInfo;

public class ChampionInfoUtil {
    public static final String url = "http://ddragon.leagueoflegends.com/cdn/10.6.1/data/en_US/champion.json";

    static class ChampionList {
        Champion[] items;
    }

    static class Champion {
        public String id;
        public int key;
    }

    public static ArrayList<ChampionInfo> parseChampionInfo(String json) {
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
