package com.example.searchlol.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.searchlol.MainActivity;
import com.example.searchlol.data.ChampionInfo;
import com.example.searchlol.utils.ChampionInfoUtil;
import com.example.searchlol.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class NameTask extends AsyncTask<Void, Void, String> {

    public interface NameCallBack {
        void onNameFinished(List<ChampionInfo> championInfo);
    }

    private String mUrl;
    private NameCallBack mNameCallBack;

    NameTask(String url, NameCallBack nameCallBack) {
        mUrl = url;
        mNameCallBack = nameCallBack;
    }

    @Override
    protected String doInBackground(Void... voids) {
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
        ArrayList<ChampionInfo> result = ChampionInfoUtil.parseChampionInfo(s);
        Log.d(TAG, "this is the result: " + result.get(1).id);
        mNameCallBack.onNameFinished(result);
    }

}
