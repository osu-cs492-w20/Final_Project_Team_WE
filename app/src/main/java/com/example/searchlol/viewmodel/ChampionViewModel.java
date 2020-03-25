package com.example.searchlol.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.searchlol.dataclass.ChampionInfo;
import com.example.searchlol.data.ChampionRepository;

import java.util.List;

public class ChampionViewModel extends ViewModel {
    private LiveData<ChampionInfo> mChampion;

    private ChampionRepository mRepository;

    public ChampionViewModel() {
        mRepository = new ChampionRepository();
        mChampion = mRepository.getChampion();
    }

    public void loadName(int id) {
        mRepository.getNameById(id);
    }

    public LiveData<ChampionInfo> getName() {
        return mChampion;
    }

}
