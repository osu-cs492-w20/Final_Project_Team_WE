package com.example.searchlol.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import static com.example.searchlol.MainActivity.trigger;
import com.example.searchlol.SummonerDetailActivity;
import com.example.searchlol.dataclass.SummonerClass;
import com.example.searchlol.utils.NetworkUtils;
import com.example.searchlol.utils.RiotSummonerUtils;

import java.io.IOException;

public class SummonerAsyncTask extends AsyncTask<String, Void, String> {
    public static String mId = "";
    private static final String TAG = SummonerAsyncTask.class.getSimpleName();
    private String mUrl;
    private String mNotRank;

    public SummonerAsyncTask(String url, String notRank) {
        mUrl = url;
        mNotRank = notRank;
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
            if (result != null) {
                mId = result.id;

                if(mId!=null) {
                    String rankURL = RiotSummonerUtils.buildRankURL(mId);
                    Log.d(TAG, "executing search with url: " + rankURL);
                    new RankAsyncTask(mNotRank).execute(rankURL);
                    String url = RiotSummonerUtils.buildMasteryURL(mId);
                    Log.d(TAG, "executing search with url: " + url);
                    new MasteryAsyncTask().execute(url);
                }
                else{
                    trigger=2;
                }
            }

        }
        mAct.receiveData(result);
    }

}
