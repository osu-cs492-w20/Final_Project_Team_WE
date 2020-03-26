package com.example.searchlol.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.searchlol.MainActivity;
import com.example.searchlol.SummonerDetailActivity;
import com.example.searchlol.dataclass.ChampionInfo;
import com.example.searchlol.dataclass.ChampionMasteryClass;
import com.example.searchlol.utils.ChampionInfoUtil;
import com.example.searchlol.utils.NetworkUtils;
import com.example.searchlol.utils.RiotSummonerUtils;

import java.io.IOException;


public class MasteryAsyncTask extends AsyncTask<String, Void, String> {

    public MasteryAsyncTask() {

    }

    @Override
    protected String doInBackground(String... strings) {
        String url = strings[0];
        String result = null;
        try {
            result = NetworkUtils.doHttpGet(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        ChampionMasteryClass result1;
        ChampionMasteryClass result2;
        ChampionMasteryClass result3;

        SummonerDetailActivity summonerDetailActivity = new SummonerDetailActivity();
        if (s != null) {
            result1 = RiotSummonerUtils.parseMasteryResult(s, 0);//json
            result2 = RiotSummonerUtils.parseMasteryResult(s, 1);//json
            result3 = RiotSummonerUtils.parseMasteryResult(s, 2);//json

            if (result1 != null && result2 != null && result3 != null) {
                summonerDetailActivity.receiveMaster(result1, result2, result3);
            }
        }
    }
}

