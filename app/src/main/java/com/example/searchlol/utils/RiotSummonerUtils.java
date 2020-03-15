package com.example.searchlol.utils;

import android.net.Uri;
import android.util.Log;

import com.example.searchlol.data.SummonerClass;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class RiotSummonerUtils {
    private final static String SUMMONERS_BASE_URL = "api.riotgames.com/lol/summoner/v4/summoners/by-name/";

    private final static String API_KEY = "RGAPI-46413c68-a044-4ef1-9e70-f7dad85c1f56";

    private final static String REGION = "na1.";

    public static String dataParsed="";
    public static String singleParsed="";
    static class SummonerSearchResults {
        ArrayList<SummonerClass> item;
    }

    public static String buildSummonerURL(String name) {
        return Uri.parse("https://" + REGION + SUMMONERS_BASE_URL).buildUpon()
                .appendPath(name)
                .appendQueryParameter("api_key", API_KEY)
                .toString();
    }

    public static SummonerClass parseSummonerResult(String json) {
        //Log.d(TAG, "onClickmy: " + json);
        Gson gson = new Gson();
        SummonerClass results = gson.fromJson(json, SummonerClass.class);
        if (results != null) {
            return results;
        } else {
            return null;
        }
    }

}
