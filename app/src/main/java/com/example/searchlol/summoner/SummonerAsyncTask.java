package com.example.searchlol.summoner;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.searchlol.data.SummonerClass;
import com.example.searchlol.utils.NetworkUtils;
import com.example.searchlol.utils.RiotSummonerUtils;

import java.io.IOException;

import static android.content.ContentValues.TAG;
import static com.example.searchlol.utils.RiotSummonerUtils.mUsername;
import static com.example.searchlol.utils.RiotSummonerUtils.mId;
import static com.example.searchlol.utils.RiotSummonerUtils.mLevel;
import static com.example.searchlol.utils.RiotSummonerUtils.mIcon;

public class SummonerAsyncTask extends AsyncTask<String, Void, String> {
    private Callback mCallback;
    private MutableLiveData<com.example.searchlol.data.Status> mLoadingStatus;

    public interface Callback {
        void onSearchFinished(SummonerClass searchResult);
    }

    public SummonerAsyncTask(Callback callback) {
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
        SummonerClass result=null;
        if (s != null) {
            result = RiotSummonerUtils.parseSummonerResult(s);//json
            mUsername = result.name;
            mId = result.id;    //encryptedsummmonerid
            mLevel = result.summonerLevel;
            mIcon = result.profileIconId;
            Log.d(TAG,"onClick: "+ mId);
        }
        mCallback.onSearchFinished(result);
    }

}
