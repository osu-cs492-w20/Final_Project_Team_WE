package com.example.searchlol.summoner;

import android.os.AsyncTask;
import android.util.Log;

import com.example.searchlol.MainActivity;
import com.example.searchlol.data.ChampionInfo;
import com.example.searchlol.utils.ChampionInfoUtil;
import com.example.searchlol.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class NameTask extends AsyncTask<String, Void, String> {
    private SummonerDetailActivity mActivity;
    public static int trigger = 0;
    public MainActivity mainActivity;


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
        mActivity = new SummonerDetailActivity();
        mainActivity = new MainActivity();

        ArrayList<ChampionInfo> result = ChampionInfoUtil.parseChampionInfo(s);
        Log.d(TAG, "this is: " + result.get(0).id);
        mActivity.getJson(result);
    }

}
