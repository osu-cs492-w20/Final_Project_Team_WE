package com.example.searchlol;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.searchlol.adapter.HistoryAdapter;
import com.example.searchlol.dataclass.MatchInfo;
import com.example.searchlol.dataclass.MatchReferenceDto;
import com.example.searchlol.utils.HistoryUtils;
import com.example.searchlol.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private static final String TAG = HistoryActivity.class.getSimpleName();
    private TextView mErrorMessageTV;
    private String mID;
    private RecyclerView historyRV;
    private HistoryAdapter mHistoryAdapter;

    private ProgressBar mLoadingIndicatorPB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_histroy);

        Intent intent = getIntent();
        mID = (String) intent.getSerializableExtra("userID");
        Log.d(TAG, mID);
        mErrorMessageTV = findViewById(R.id.tv_error_message);


        mHistoryAdapter = new HistoryAdapter();
        historyRV = findViewById(R.id.history_RV);
        historyRV.setAdapter(mHistoryAdapter);
        historyRV.setLayoutManager(new LinearLayoutManager(this));
        historyRV.setHasFixedSize(true);

        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        matchHistorySearch();
        Log.d(TAG, "in History");

    }


    private void matchHistorySearch() {
        String url = HistoryUtils.buildHistoryListSearchURL(mID);
        Log.d(TAG, "querying url: " + url);
        new HistorySearchTask().execute(url);
    }

    public class HistorySearchTask extends AsyncTask<String, Void, ArrayList<MatchInfo>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicatorPB.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<MatchInfo> doInBackground(String... strings) {
            String matchList = strings[0];
            String searchResults = null;
            try {
                searchResults = NetworkUtils.doHttpGet(matchList);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<MatchReferenceDto> searchResultsList = HistoryUtils.parseHistoryListResults(searchResults);
            ArrayList<MatchInfo> matchInfos = new ArrayList();

            if (searchResults != null) {
                for (MatchReferenceDto info : searchResultsList) {
                    String matchDetail = HistoryUtils.buildOneMatchURL(info.gameId);
                    String matchResults = null;

                    try {
                        matchResults = NetworkUtils.doHttpGet(matchDetail);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    assert matchInfos != null;

                    matchInfos.add(HistoryUtils.parseOneMatchResults(matchResults));
                    Log.d(TAG, String.valueOf(matchInfos.get(0).champ));
                }
                return matchInfos;
            } else {
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<MatchInfo> s) {
            super.onPostExecute(s);
            if (s != null) {
                mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                mHistoryAdapter.updateMatchinfo(s);
                mErrorMessageTV.setVisibility(View.INVISIBLE);
                historyRV.setVisibility(View.VISIBLE);
            } else {
                mErrorMessageTV.setVisibility(View.VISIBLE);
                historyRV.setVisibility(View.INVISIBLE);
            }
        }
    }
}
