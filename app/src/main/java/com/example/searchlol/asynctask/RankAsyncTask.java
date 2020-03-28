package com.example.searchlol.asynctask;

import android.os.AsyncTask;

import com.example.searchlol.R;
import com.example.searchlol.SummonerDetailActivity;
import com.example.searchlol.dataclass.RankClass;
import com.example.searchlol.utils.NetworkUtils;
import com.example.searchlol.utils.RiotSummonerUtils;

import java.io.IOException;


public class RankAsyncTask extends AsyncTask<String, Void, String> {

    private String notRank;

    public RankAsyncTask(String notRank) {
         this.notRank = notRank;
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
        RankClass result;
        SummonerDetailActivity mAct;
        if (s != null) {
            mAct = new SummonerDetailActivity();
            result = RiotSummonerUtils.parseRankResult(s);//json

            if (result != null) {
                mAct.receiveRank(result);
            } else {
                String unRank = notRank;
                mAct.implementRank(unRank);
            }
        }
    }
}

