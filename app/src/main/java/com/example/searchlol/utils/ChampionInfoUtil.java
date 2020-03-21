package com.example.searchlol.utils;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import com.example.searchlol.dataclass.ChampionInfo;

public class ChampionInfoUtil {
    public static final String url = "https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champions/";
    public static final String picFormat = ".png";

    static class ChampionJson {
        public String version;
        ChampionList[] data;
    }

    static class ChampionList {
        Champion[] data;
    }

    static class Champion {
        public String id;
        public int key;
    }

    public static String buildChampionInfoURL(String championId) {
        return Uri.parse(url).buildUpon()
                .appendPath(championId)
                .appendPath(picFormat)
                .build()
                .toString();
    }

    public static ArrayList<ChampionInfo> parseChampionInfo(String json) {
        Gson gson = new Gson();
        Log.d("TAG", "parseChampionInfo: " + json);
        ChampionList results = gson.fromJson(json, ChampionList.class);
        Log.d("TAG", "parseID: " + results.data);
        if (results.data != null) {
            ArrayList<ChampionInfo> championLists = new ArrayList<>();
            for (Champion champion : results.data) {
                ChampionInfo championInfo = new ChampionInfo();

                championInfo.id = champion.id;
                championInfo.key = champion.key;

                championLists.add(championInfo);
            }
            return championLists;
        } else {
            return null;
        }
    }
}
