package com.example.searchlol.data;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.searchlol.summoner.SummonerAsyncTask;
import com.example.searchlol.utils.RiotSummonerUtils;

public class SummonerSearchRepository implements SummonerAsyncTask.Callback {
    private static final String TAG = SummonerSearchRepository.class.getSimpleName();
    private MutableLiveData<SummonerClass> mSearchResults;
    private MutableLiveData<Status> mLoadingStatus;
    private String mCurrentQuery;

    public SummonerSearchRepository() {
        mSearchResults = new MutableLiveData<>();
        mSearchResults.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);
    }

    public LiveData<SummonerClass> getSearchResults() {
        return mSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
    }

    @Override
    public void onSearchFinished(SummonerClass searchResults) {
        mSearchResults.setValue(searchResults);
        if (searchResults != null) {
            mLoadingStatus.setValue(Status.SUCCESS);
        }
    }

    private boolean shouldExecuteSearch(String query) {
        return !TextUtils.equals(query, mCurrentQuery);
    }

    public void loadSearchResults(String query) {//summoner name
        if (shouldExecuteSearch(query)) {
            mCurrentQuery = query;
            String url = RiotSummonerUtils.buildSummonerURL(query);
            mSearchResults.setValue(null);
            Log.d(TAG, "executing search with url: " + url);
            mLoadingStatus.setValue(Status.LOADING);///loadingindicator
            new SummonerAsyncTask(this).execute(url);
        } else {
            Log.d(TAG, "using cached search results");
        }
    }
}
