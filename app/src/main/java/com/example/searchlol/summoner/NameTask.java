package com.example.searchlol.summoner;


import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.searchlol.MainActivity;
import com.example.searchlol.data.ChampionInfo;
import com.example.searchlol.data.SummonerClass;
import com.example.searchlol.utils.ChampionInfoUtil;
import com.example.searchlol.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class NameTask extends AsyncTask<String, Void, String> {
    private Callback mCallback;
    private SummonerDetailActivity mActivity;
    public static int trigger=0;
    public MainActivity mainActivity;
    private SummonerClass summonerClass;
    private MutableLiveData<com.example.searchlol.data.Status> mLoadingStatus;
    public interface Callback {
        void onSearchFinished(ArrayList<ChampionInfo> searchResult);
    }

    public NameTask(Callback callback) {
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
        ArrayList<ChampionInfo> result = null;
        mActivity = new SummonerDetailActivity();
        summonerClass = new SummonerClass();
        mainActivity = new MainActivity();
        if (s != null) {
            result = ChampionInfoUtil.parseChampionInfo(s);
            Log.d(TAG, "this is: " + result);
            mActivity.receiveName(result);
            mainActivity.trigger=1;
        }
        mCallback.onSearchFinished(result);

    }

}
