package com.example.searchlol.summoner;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.searchlol.MainActivity;
import com.example.searchlol.data.ChampionInfo;
import com.example.searchlol.data.ChampionMasteryClass;
import com.example.searchlol.data.SummonerClass;
import com.example.searchlol.utils.ChampionInfoUtil;
import com.example.searchlol.utils.NetworkUtils;
import com.example.searchlol.utils.RiotSummonerUtils;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class ChampionAsyncTask extends AsyncTask<String, Void, String> {
    private Callback mCallback;
    private SummonerDetailActivity mActivity;
    public static int trigger=0;
    public MainActivity mainActivity;
    private SummonerClass summonerClass;
    private MutableLiveData<com.example.searchlol.data.Status> mLoadingStatus;
    public interface Callback {
        void onSearchFinished(ChampionMasteryClass searchResult);
    }

    public ChampionAsyncTask(Callback callback) {
        mCallback = callback;
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
        ChampionMasteryClass result = null;
        ChampionMasteryClass result1 = null;
        ChampionMasteryClass result2 = null;
        mActivity = new SummonerDetailActivity();
        summonerClass = new SummonerClass();
        mainActivity = new MainActivity();
        if (s != null) {
                result = RiotSummonerUtils.parseMasteryResult(s,0);//json
                Log.d(TAG, "this is: " + result.championLevel);
                result1= RiotSummonerUtils.parseMasteryResult(s,1);//json
                Log.d(TAG, "this is: " + result1.championLevel);
                result2= RiotSummonerUtils.parseMasteryResult(s,2);//json
                Log.d(TAG, "this is: " + result2.championLevel);
                mActivity.receiveMaster(result,result1,result2);
                mainActivity.trigger=1;
        }
        mCallback.onSearchFinished(result);

    }

}
