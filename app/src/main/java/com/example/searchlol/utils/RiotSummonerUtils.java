package com.example.searchlol.utils;

import android.net.Uri;

import com.example.searchlol.dataclass.ChampionMasteryClass;
import com.example.searchlol.dataclass.RankClass;
import com.example.searchlol.dataclass.SummonerClass;
import com.google.gson.Gson;
import static com.example.searchlol.asynctask.SummonerAsyncTask.mId;

public class RiotSummonerUtils {
    private final static String SUMMONERS_BASE_URL = "api.riotgames.com/lol/summoner/v4/summoners/by-name/";
    private final static String SUMMONERS_CHAMPION_URL = "api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/"; //encryptedid->id
    private final static String SUMMONERS_RANK_URL = "api.riotgames.com/lol/league/v4/entries/by-summoner/";

    private final static String API_KEY = "RGAPI-cbab7df3-8f08-41ab-8b12-e6a246a09224";
    private static String REGION = "NA1.";

    public void changeRegion(String region){
        REGION=region;
    }

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

    public static String buildRankURL(String name){
        return Uri.parse("https://" + REGION + SUMMONERS_RANK_URL).buildUpon()
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

    public static RankClass parseRankResult(String json) {
        Gson gson = new Gson();
        RankClass results[] = gson.fromJson(json, RankClass[].class);

        try{
            return results[0];
        }
        catch(ArrayIndexOutOfBoundsException exception){
            return null;
        }

    }

}
