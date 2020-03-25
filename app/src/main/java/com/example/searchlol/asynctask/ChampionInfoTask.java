package com.example.searchlol.asynctask;

import android.os.AsyncTask;

import com.example.searchlol.dataclass.ChampionInfo;
import com.example.searchlol.utils.ChampionInfoUtil;
import com.example.searchlol.utils.NetworkUtils;

import java.io.IOException;

public class ChampionInfoTask extends AsyncTask<Void, Void, String> {

    public interface NameCallBack {
        void onNameFinished(ChampionInfo championInfo);
    }

    private String mUrl;
    private NameCallBack mNameCallBack;

    public ChampionInfoTask(String url, NameCallBack nameCallBack) {
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
        ChampionInfo result = ChampionInfoUtil.parseChampionInfo(s);
        mNameCallBack.onNameFinished(result);
    }

}
