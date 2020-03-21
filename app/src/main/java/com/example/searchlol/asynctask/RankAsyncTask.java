package com.example.searchlol.asynctask;

import android.os.AsyncTask;

import com.example.searchlol.SummonerDetailActivity;
import com.example.searchlol.dataclass.RankClass;
import com.example.searchlol.utils.NetworkUtils;
import com.example.searchlol.utils.RiotSummonerUtils;

import java.io.IOException;

public class RankAsyncTask extends AsyncTask<String, Void, String> {
    private Callback mCallback;
    private SummonerDetailActivity mAct;
    public interface Callback {
        void onSearchFinished2(RankClass searchResult);
    }

    public RankAsyncTask(Callback callback) {
        mCallback = callback;
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
        RankClass result=null;

        if (s != null) {
            mAct= new SummonerDetailActivity();
            result = RiotSummonerUtils.parseRankResult(s);//json
            mAct.receiveRank(result);
        }
        mCallback.onSearchFinished2(result);

    }

}

