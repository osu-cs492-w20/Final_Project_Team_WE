package com.example.searchlol.summoner;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.searchlol.data.RankClass;
import com.example.searchlol.data.SummonerClass;
import com.example.searchlol.utils.NetworkUtils;
import com.example.searchlol.utils.RiotSummonerUtils;
import com.example.searchlol.utils.SummonerRankUtils;

import java.io.IOException;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.searchlol.utils.SummonerRankUtils.mTier;
import static com.example.searchlol.utils.SummonerRankUtils.mRank;
import static com.example.searchlol.utils.SummonerRankUtils.mWins;
import static com.example.searchlol.utils.SummonerRankUtils.mLosses;

public class RankAsyncTask extends AsyncTask<String, Void, String> {
    private Callback mCallback;
    private MutableLiveData<com.example.searchlol.data.Status> mLoadingStatus;

    public interface Callback {
        void onSearchFinished(List<RankClass> searchResult);
    }

    public RankAsyncTask(Callback callback) {
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
        List<RankClass> result = null;
        if (s != null) {
            result = SummonerRankUtils.parseRankSearchResults(s);//json
            mTier = result.get(0).tier;
            mRank = result.get(0).rank;    //encryptedsummmonerid
            mWins = result.get(0).wins;
            mLosses = result.get(0).losses;
            Log.d(TAG, "onClick: " + mRank + mTier);
        }
        mCallback.onSearchFinished(result);
    }

}
