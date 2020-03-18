package com.example.searchlol.utils;

import android.net.Uri;

import com.example.searchlol.data.RankClass;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.example.searchlol.utils.RiotSummonerUtils.mId;

public class SummonerRankUtils {
    private final static String SUMMONERS_RANK_URL = "api.riotgames.com/lol/league/v4/entries/by-summoner/";
    private final static String API_KEY = "RGAPI-ee2db9b1-12f0-498c-afdb-6ce35599b614";

    private final static String REGION = "na1.";

    public static String mTier = "";
    public static String mRank = "";
    public static int mWins = 0;
    public static int mLosses = 0;

    static class RankSearchResults {
        ArrayList<RankClass> items;
    }

    public static String buildRankSearchURL(String name) {
        return Uri.parse("https://" + REGION + SUMMONERS_RANK_URL).buildUpon()
                .appendPath(mId)
                .appendQueryParameter("api_key", API_KEY)
                .toString();
    }

    public static ArrayList<RankClass> parseRankSearchResults(String json) {
        Gson gson = new Gson();
        RankSearchResults results = gson.fromJson(json, RankSearchResults.class);
        if (results != null && results.items != null) {
            return results.items;
        } else {
            return null;
        }
    }
}
