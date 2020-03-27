package com.example.searchlol.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.searchlol.SummonerDetailActivity;
import com.example.searchlol.dataclass.RankClass;
import com.example.searchlol.utils.NetworkUtils;
import com.example.searchlol.utils.RiotSummonerUtils;

import java.io.IOException;

import static com.example.searchlol.ChampionDetailActivity.TAG;

public class RankAsyncTask extends AsyncTask<String, Void, String> {

    public RankAsyncTask() {
    }

    @Override
    protected java.lang.String doInBackground(java.lang.String... strings) {
        java.lang.String url = strings[0];
        java.lang.String result = null;
        try {
            result = NetworkUtils.doHttpGet(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(java.lang.String s) {
        RankClass result;
        SummonerDetailActivity mAct;
        if (s != null) {
            mAct = new SummonerDetailActivity();
            result = RiotSummonerUtils.parseRankResult(s);//json

            if (result != null) {
                mAct.receiveRank(result);
            }
            else{
                String unRank="UnRanked";
                mAct.implementRank(unRank);
            }
        }
    }
}

