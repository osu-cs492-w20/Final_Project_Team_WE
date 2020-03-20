package com.example.searchlol.summoner;

import android.os.AsyncTask;
import android.util.Log;
import com.example.searchlol.data.RankClass;
import com.example.searchlol.utils.NetworkUtils;
import com.example.searchlol.utils.RiotSummonerUtils;

import java.io.IOException;

import static android.content.ContentValues.TAG;

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

