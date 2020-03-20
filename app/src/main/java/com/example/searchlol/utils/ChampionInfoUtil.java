package com.example.searchlol.utils;

import android.os.AsyncTask;
import android.util.Log;

import com.example.searchlol.data.ChampionInfoList;
import com.google.gson.Gson;
import com.example.searchlol.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ChampionInfoUtil {
    private String mInfo = null;
    private final static String url = "http://ddragon.leagueoflegends.com/cdn/10.6.1/data/en_US/champion.json";

    public String getChampionById(int id) {
        String championName = null;

//        for(int i = 0; i < mInfo.size(); i++){
//            if (id == mInfo.get(i).key) {
//                championName = mInfo.get(i).id;
//            }
//        }

        ArrayList<ChampionInfoList.ChampionInfo> championInfos = parseChampionInfo();
        Log.d(TAG, "getChampionById: " + mInfo);
        return championName;
    }


    public ArrayList<ChampionInfoList.ChampionInfo> parseChampionInfo() {
        new ChampionTask().execute(url);
        Log.d(TAG, "parseChampionInfo: " + mInfo);
//        for (int i = 0; i < results.data.size(); i++) {
//            championInfos.add(results.data.get(i));
//        }
//        if (results != null) {
//            return championInfos;
//        } else {
            return null;
//        }
    }

    public class ChampionTask extends AsyncTask<String, Void, String> {

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
            mInfo = s;
        }
    }
}
