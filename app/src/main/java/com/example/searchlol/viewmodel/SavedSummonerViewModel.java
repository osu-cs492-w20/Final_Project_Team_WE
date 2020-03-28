package com.example.searchlol.viewmodel;

import android.app.Application;

import com.example.searchlol.data.SavedSummonerRepository;
import com.example.searchlol.dataclass.SummonerClass;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SavedSummonerViewModel extends AndroidViewModel {
    private SavedSummonerRepository mRepository;

    public SavedSummonerViewModel(Application application) {
        super(application);
        mRepository = new SavedSummonerRepository(application);
    }

    public void insertSavedSummoner(SummonerClass summoner) {
        mRepository.insertSavedSummoner(summoner);
    }

    public void deleteSavedSummoner(SummonerClass summoner) {
        mRepository.deleteSavedSummoner(summoner);
    }

    public LiveData<List<SummonerClass>> getAllSummoners() {
        return mRepository.getAllSummoners();
    }

    public boolean getSummonerByName(String fullName) {
        return mRepository.getSummonerByName(fullName);
    }

}