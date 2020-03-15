package com.example.searchlol.summoner;

import android.os.AsyncTask;

import com.example.searchlol.data.SummonerClass;
import com.example.searchlol.utils.NetworkUtils;
import com.example.searchlol.utils.RiotSummonerUtils;

import java.io.IOException;

public class SummonerAsyncTask extends AsyncTask<String, Void, String> {
    private Callback mCallback;

    public interface Callback {
        void onSearchFinished(SummonerClass searchResult);
    }

    public SummonerAsyncTask(Callback callback) { mCallback = callback; }

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
        SummonerClass result = null;
        if (s != null) {
            result = RiotSummonerUtils.parseSummonerResult(s);
        }
        mCallback.onSearchFinished(result);
    }
}
