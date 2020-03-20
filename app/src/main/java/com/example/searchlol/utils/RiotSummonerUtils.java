package com.example.searchlol.utils;

import android.net.Uri;

import com.example.searchlol.data.ChampionMasteryClass;
import com.example.searchlol.data.SummonerClass;
import com.google.gson.Gson;
import static com.example.searchlol.summoner.SummonerAsyncTask.mId;

public class RiotSummonerUtils {
    private final static String SUMMONERS_BASE_URL = "api.riotgames.com/lol/summoner/v4/summoners/by-name/";
    private final static String SUMMONERS_CHAMPION_URL = "api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/"; //encryptedid->id
    private final static String API_KEY = "RGAPI-c6dd02e7-edf7-4880-b0ce-3ec9b6b60a15";
    private final static String REGION = "na1.";

    public static String buildSummonerURL(String name) {
            return Uri.parse("https://" + REGION + SUMMONERS_BASE_URL).buildUpon()
                    .appendPath(name)
                    .appendQueryParameter("api_key", API_KEY)
                    .toString();
    }

    public static String buildMasteryURL(String name){
            return Uri.parse("https://" + REGION + SUMMONERS_CHAMPION_URL).buildUpon()
                    .appendPath(mId)
                    .appendQueryParameter("api_key", API_KEY)
                    .toString();
    }

    public static SummonerClass parseSummonerResult(String json) {
        Gson gson = new Gson();
        SummonerClass results = gson.fromJson(json, SummonerClass.class);
        if (results != null) {
            return results;
        } else {
            return null;
        }
    }

    public static ChampionMasteryClass parseMasteryResult(String json, int num) {
        Gson gson = new Gson();
        ChampionMasteryClass[] results = gson.fromJson(json, ChampionMasteryClass[].class);
        if (results != null) {
            return results[num];
        } else {
            return null;
        }
    }

}
