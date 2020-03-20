package com.example.searchlol.utils;

import android.os.AsyncTask;

import com.example.searchlol.data.ChampionInfoList;
import com.google.gson.Gson;
import com.example.searchlol.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;

public class ChampionInfoUtil {
    public static ArrayList<ChampionInfoList.ChampionInfo> mInfo;

    public static void updateJsonString(ArrayList<ChampionInfoList.ChampionInfo> info){
       mInfo = info;
    }

    public static String getChampionById(int id) {
        String championName = null;
        String url = "http://ddragon.leagueoflegends.com/cdn/10.6.1/data/en_US/champion.json";

        new ChampionTask().execute(url);
        for(int i = 0; i < mInfo.size(); i++){
            if (id == mInfo.get(i).key) {
                championName = mInfo.get(i).id;
            }
        }
        return championName;
    }


    public static ArrayList<ChampionInfoList.ChampionInfo> parseChampionInfo(String json) {
        ArrayList<ChampionInfoList.ChampionInfo> championInfos = new ArrayList<>();
        ChampionInfoList results = new Gson().fromJson(json, ChampionInfoList.class);
        for (int i = 0; i < results.data.size(); i++) {
            championInfos.add(results.data.get(i));
        }
        if (results != null) {
            return championInfos;
        } else {
            return null;
        }
        //return championName;
    }

    public static class ChampionTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            String json = null;
            try {
                json = NetworkUtils.doHttpGet(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ArrayList<ChampionInfoList.ChampionInfo> championInfos = parseChampionInfo(s);
            updateJsonString(championInfos);
        }
    }
}
