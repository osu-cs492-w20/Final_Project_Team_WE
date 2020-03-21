package com.example.searchlol.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.searchlol.asynctask.NameTask;
import com.example.searchlol.dataclass.ChampionInfo;
import com.example.searchlol.utils.ChampionInfoUtil;

import java.util.List;

public class ChampionRepository implements NameTask.NameCallBack {
    private MutableLiveData<List<ChampionInfo>> mChampion;

    public ChampionRepository() {
        mChampion = new MutableLiveData<>();
        mChampion.setValue(null);
    }

    public void getNameById(int id){
        String url = ChampionInfoUtil.buildChampionInfoURL(String.valueOf(id));
        new NameTask(url, this).execute();
    }

    public LiveData<List<ChampionInfo>> getChampion() {
        return mChampion;
    }

    @Override
    public void onNameFinished(List<ChampionInfo> championInfo) {
        mChampion.setValue(championInfo);
    }
}
