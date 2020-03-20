package com.example.searchlol.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.jar.Attributes;

public class ChampionRepository implements NameTask.NameCallBack {
    private MutableLiveData<List<ChampionInfo>> mChampion;

    public ChampionRepository() {
        mChampion = new MutableLiveData<>();
        mChampion.setValue(null);
    }

    public void getNameById(int id){
        String url = "https://ddragon.leagueoflegends.com/cdn/10.6.1/data/en_US/champion.json";
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
