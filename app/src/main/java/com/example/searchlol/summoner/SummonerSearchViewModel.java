package com.example.searchlol.summoner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.searchlol.data.RankClass;
import com.example.searchlol.data.RankSearchRepository;
import com.example.searchlol.data.SummonerClass;
import com.example.searchlol.data.SummonerSearchRepository;
import com.example.searchlol.data.Status;

import java.util.List;

public class SummonerSearchViewModel extends ViewModel {
    private SummonerSearchRepository mSummonerRepository;
    private RankSearchRepository mRankRepository;
    private LiveData<List<SummonerClass>> mSearchResults;
    private LiveData<List<RankClass>> mRankResults;
    private LiveData<Status> mLoadingStatus;

    public SummonerSearchViewModel() {
        mSummonerRepository = new SummonerSearchRepository();
        mRankRepository = new RankSearchRepository();
        mSearchResults = mSummonerRepository.getSearchResults();
        mLoadingStatus = mSummonerRepository.getLoadingStatus();
    }

    public void loadSummonerSearchResults(String query) {
        mSummonerRepository.loadSummonerSearchResults(query);
    }

    public void loadRankSearchResults(String Id) {
        mRankRepository.loadRankSearchResults(Id);
    }

    public LiveData<List<SummonerClass>> getSummonerSearchResults() {
        return mSearchResults;
    }

    public LiveData<List<RankClass>> getRankSearchResults() {
        return mRankResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

}
