package com.example.searchlol.data;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.searchlol.asynctask.SummonerAsyncTask;
import com.example.searchlol.dataclass.SummonerClass;
import com.example.searchlol.utils.RiotSummonerUtils;
import static com.example.searchlol.MainActivity.trigger;

public class SummonerSearchRepository {
    private static final String TAG = SummonerSearchRepository.class.getSimpleName();
    private MutableLiveData<SummonerClass> mSearchResults;
    private MutableLiveData<Status> mLoadingStatus;
    private String mCurrentQuery;
    private String notRank;

    public SummonerSearchRepository(String notRank) {
        mSearchResults = new MutableLiveData<>();
        mSearchResults.setValue(null);

        mLoadingStatus = new MutableLiveData<>();
        mLoadingStatus.setValue(Status.SUCCESS);
        this.notRank = notRank;
    }

    public LiveData<SummonerClass> getSearchResults() {
        return mSearchResults;
    }

    public LiveData<Status> getLoadingStatus() {
        return mLoadingStatus;
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
            new SummonerAsyncTask(url, notRank).execute();
        } else {
            Log.d(TAG, "using cached search results");
            trigger=1;
        }
    }
}
