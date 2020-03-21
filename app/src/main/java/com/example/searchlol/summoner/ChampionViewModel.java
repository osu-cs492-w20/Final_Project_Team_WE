package com.example.searchlol.summoner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.searchlol.dataclass.ChampionInfo;
import com.example.searchlol.data.ChampionRepository;

import java.util.List;

public class ChampionViewModel extends ViewModel {
    private LiveData<List<ChampionInfo>> mChampion;

    private ChampionRepository mRepository;

    public ChampionViewModel() {
        mRepository = new ChampionRepository();
        mChampion = mRepository.getChampion();
    }

    public void loadName(int id) {
        mRepository.getNameById(id);
    }

    public LiveData<List<ChampionInfo>> getName() {
        return mChampion;
    }

}
