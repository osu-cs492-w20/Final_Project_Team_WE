package com.example.searchlol.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.searchlol.SummonerDetailActivity;
import com.example.searchlol.dataclass.SummonerClass;
import com.example.searchlol.utils.NetworkUtils;
import com.example.searchlol.utils.RiotSummonerUtils;

import java.io.IOException;

public class SummonerAsyncTask extends AsyncTask<String, Void, String> {
    public static String mId = "";
    private static final String TAG = SummonerAsyncTask.class.getSimpleName();
    private String mUrl;


    public SummonerAsyncTask(String url) {
        mUrl = url;
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = null;
        try {
            result = NetworkUtils.doHttpGet(mUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        SummonerClass result = null;
        SummonerDetailActivity mAct;
        mAct = new SummonerDetailActivity();
        if (s != null) {
            result = RiotSummonerUtils.parseSummonerResult(s);//json
            if (result != null)
                mId = result.id;
            String rankURL = RiotSummonerUtils.buildRankURL(mId);
            Log.d(TAG, "executing search with url: " + rankURL);
            new RankAsyncTask().execute(rankURL);
            String url = RiotSummonerUtils.buildMasteryURL(mId);
            Log.d(TAG, "executing search with url: " + url);
            new MasteryAsyncTask().execute(url);
        }
        mAct.receiveData(result);
    }

}
