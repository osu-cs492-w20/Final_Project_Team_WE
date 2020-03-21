package com.example.searchlol.summoner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.searchlol.dataclass.SummonerClass;
import com.example.searchlol.data.SummonerSearchRepository;
import com.example.searchlol.data.Status;

public class SummonerSearchViewModel extends ViewModel {
    private SummonerSearchRepository mRepository;
    private LiveData<SummonerClass> mSearchResults;
    private LiveData<Status> mLoadingStatus;

    public SummonerSearchViewModel() {
        mRepository = new SummonerSearchRepository();
        mSearchResults = mRepository.getSearchResults();
        mLoadingStatus = mRepository.getLoadingStatus();
    }

    public void loadSearchResults(String query) {
        mRepository.loadSearchResults(query);
    }

    public LiveData<SummonerClass> getSearchResults() {
        return mSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }
}
