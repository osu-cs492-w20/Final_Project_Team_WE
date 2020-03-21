package com.example.searchlol.asynctask;

import android.os.AsyncTask;

import com.example.searchlol.dataclass.ChampionInfo;
import com.example.searchlol.utils.ChampionInfoUtil;
import com.example.searchlol.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        mNameCallBack.onNameFinished(result);
    }

}
