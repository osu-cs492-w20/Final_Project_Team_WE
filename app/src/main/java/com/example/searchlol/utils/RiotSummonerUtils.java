package com.example.searchlol.utils;

import android.net.Uri;

public class RiotSummonerUtils {
    private final static String SUMMONERS_BASE_URL = "api.riotgames.com/lol/summoner/v4/summoners/by-name/";

    private final static String API_KEY = "RGAPI-20b21fea-e08a-4a25-abad-a6fa7a0265c1";

    private final static String REGION = "na1.";

    public static String buildSummonerURL(String name) {
        return Uri.parse("https://" + REGION + SUMMONERS_BASE_URL).buildUpon()
                .appendPath(name)
                .appendQueryParameter("api_key", API_KEY)
                .toString();
    }

}
