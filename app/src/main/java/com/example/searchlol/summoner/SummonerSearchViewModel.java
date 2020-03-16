package com.example.searchlol.summoner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.searchlol.data.SummonerClass;
import com.example.searchlol.data.SummonerSearchRepository;
import com.example.searchlol.data.Status;

import java.util.List;

public class SummonerSearchViewModel extends ViewModel {
    private SummonerSearchRepository mRepository;
    private LiveData<List<SummonerClass>> mSearchResults;
    private LiveData<Status> mLoadingStatus;

    public SummonerSearchViewModel() {
        mRepository = new SummonerSearchRepository();
        mSearchResults = mRepository.getSearchResults();
        mLoadingStatus = mRepository.getLoadingStatus();
    }

    public void loadSearchResults(String query) {
        mRepository.loadSearchResults(query);
    }

    public LiveData<List<SummonerClass>> getSearchResults() {
        return mSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }
}
