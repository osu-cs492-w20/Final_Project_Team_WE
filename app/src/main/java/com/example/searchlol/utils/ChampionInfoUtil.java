package com.example.searchlol.utils;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import com.example.searchlol.dataclass.ChampionInfo;

public class ChampionInfoUtil {
    public static final String url = "https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champions/";

    static class Champion {
        public int id;
        public String name;
        public String title;
        public String shortBio;

    }

    public static String buildChampionInfoURL(int championId) {
        return Uri.parse(url).buildUpon()
                .appendPath(championId + ".json")
                .build()
                .toString();
    }

    public static ChampionInfo parseChampionInfo(String json) {
        Gson gson = new Gson();
        Champion results = gson.fromJson(json, Champion.class);
        if (results != null) {
            ChampionInfo championInfo = new ChampionInfo();
            championInfo.id = results.id;
            championInfo.name = results.name;
            championInfo.shortBio = results.shortBio;
            return championInfo;
        } else {
            return null;
        }
    }
}
