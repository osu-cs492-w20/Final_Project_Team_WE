package com.example.searchlol.asynctask;

import android.os.AsyncTask;
import android.util.Log;
import static android.content.ContentValues.TAG;
import androidx.lifecycle.MutableLiveData;

import com.example.searchlol.SummonerDetailActivity;
import com.example.searchlol.dataclass.ChampionMasteryClass;
import com.example.searchlol.dataclass.RankClass;
import com.example.searchlol.dataclass.SummonerClass;
import com.example.searchlol.utils.NetworkUtils;
import com.example.searchlol.utils.RiotSummonerUtils;
import java.io.IOException;

public class SummonerAsyncTask extends AsyncTask<String, Void, String> implements ChampionAsyncTask.Callback, RankAsyncTask.Callback {
    private Callback mCallback;
    private MutableLiveData<com.example.searchlol.data.Status> mLoadingStatus;
    public SummonerClass mrepo;
    public SummonerDetailActivity mAct;
    public static String mId="";
    public interface Callback {
        void onSearchFinished(SummonerClass searchResult);
    }

    public SummonerAsyncTask(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onSearchFinished(ChampionMasteryClass searchResults) {
        if (searchResults != null) {
            Log.d(TAG,"Received!");
        }
    }

    @Override
    public void onSearchFinished2(RankClass searchResults) {
        if (searchResults != null) {
            Log.d(TAG,"Received!");
        }
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
        SummonerClass result=null;
        mrepo= new SummonerClass();
        mAct  = new SummonerDetailActivity();
        if (s != null) {
            result = RiotSummonerUtils.parseSummonerResult(s);//json
            mId = result.id;
            String newurl= RiotSummonerUtils.buildRankURL(mId);
            Log.d(TAG, "executing search with url: " + newurl);
            new RankAsyncTask(this).execute(newurl);
            String url = RiotSummonerUtils.buildMasteryURL(mId);
            Log.d(TAG, "executing search with url: " + url);
            new ChampionAsyncTask(this).execute(url);
        }
        mCallback.onSearchFinished(result);
        mAct.receiveData(result);
    }

}
