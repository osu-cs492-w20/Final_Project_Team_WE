package com.example.searchlol.data;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.searchlol.summoner.RankAsyncTask;
import com.example.searchlol.utils.SummonerRankUtils;

import java.util.List;

public class RankSearchRepository implements RankAsyncTask.Callback {
    private static final String TAG = SummonerSearchRepository.class.getSimpleName();
    private MutableLiveData<List<SummonerClass>> mSearchResults;
    private MutableLiveData<Status> mLoadingStatus;

    private String mCurrentQuery;

    public RankSearchRepository() {
        mSearchResults = new MutableLiveData<>();
        mSearchResults.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);
    }

    public LiveData<List<SummonerClass>> getSearchResults() {
        return mSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    @Override
    public void onSearchFinished(List<RankClass> searchResults) {
        //mSearchResults.setValue(searchResults);
        if (searchResults != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        }
    }

    private boolean shouldExecuteSearch(String query) {
        return !TextUtils.equals(query, mCurrentQuery);
    }

    public void loadRankSearchResults(String Id) {//summoner name
        //preference //multi buildURL
        if (shouldExecuteSearch(Id)) {
            mCurrentQuery = Id;
            String url = SummonerRankUtils.buildRankSearchURL(Id);
            mSearchResults.setValue(null);
            Log.d(TAG, "executing search with url: " + url);
            mLoadingStatus.setValue(Status.LOADING);///loadingindicator
            new RankAsyncTask(this).execute(url);
        } else {
            Log.d(TAG, "using cached search results");
        }
    }
}
