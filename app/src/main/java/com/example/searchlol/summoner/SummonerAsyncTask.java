package com.example.searchlol.summoner;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.searchlol.data.SummonerClass;
import com.example.searchlol.utils.NetworkUtils;
import com.example.searchlol.utils.RiotSummonerUtils;

import java.io.IOException;

import static android.content.ContentValues.TAG;
import static com.example.searchlol.utils.RiotSummonerUtils.dataParsed;
import static com.example.searchlol.utils.RiotSummonerUtils.singleParsed;

public class SummonerAsyncTask extends AsyncTask<String, Void, String> {
    private Callback mCallback;
    public RiotSummonerUtils mvalues;
    public RepoDetailActivity mRepo;
    public static String myJson="";
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
        //SummonerClass result = null;
        SummonerClass result=null;
        if (s != null) {
            result = RiotSummonerUtils.parseSummonerResult(s);//json
            dataParsed = result.name;
            singleParsed = result.id;
            //Log.d(TAG,"onClick: "+ dataParsed);
            Log.d(TAG,"onClick: "+ singleParsed);
        }
        mCallback.onSearchFinished(result);
    }

}
