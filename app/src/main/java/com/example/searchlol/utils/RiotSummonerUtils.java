package com.example.searchlol.utils;

import android.net.Uri;
import android.util.Log;

import com.example.searchlol.data.SummonerClass;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class RiotSummonerUtils {
    private final static String SUMMONERS_BASE_URL = "api.riotgames.com/lol/summoner/v4/summoners/by-name/";
    private final static String SUMMONERS_CHAMPION_URL = "api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-summoner/"; //encryptedid->id
    private final static String API_KEY = "RGAPI-0d300a58-5265-4a57-b23f-d721412dec92";

    private final static String REGION = "na1.";

    public static String mUsername ="";
    public static String mId ="";
    public static String mLevel ="";
    public static int mIcon=0;
    public static int rotate=0;

    public static String buildSummonerURL(String name) {
        if(rotate==1){
            return Uri.parse("https://" + REGION + SUMMONERS_CHAMPION_URL).buildUpon()
                    .appendPath(mId)
                    .appendQueryParameter("api_key", API_KEY)
                    .toString();
        }
        else {
            return Uri.parse("https://" + REGION + SUMMONERS_BASE_URL).buildUpon()
                    .appendPath(name)
                    .appendQueryParameter("api_key", API_KEY)
                    .toString();
        }
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
