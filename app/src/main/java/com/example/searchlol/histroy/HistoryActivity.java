package com.example.searchlol.histroy;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.searchlol.R;
import com.example.searchlol.utils.HistoryUtils;
import com.example.searchlol.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;

public class HistoryActivity  extends AppCompatActivity {
    private static final String TAG = HistoryActivity.class.getSimpleName();
    private TextView history;
    private String mID;
    private RecyclerView historyRV;
    private HistoryAdapter mHistoryAdapter;

//    private ProgressBar mLoadingIndicatorPB;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_histroy);

        Intent intent = getIntent();
        mID = (String)intent.getSerializableExtra("userID");
        Log.d(TAG,mID);
        history=findViewById(R.id.history);


        mHistoryAdapter = new HistoryAdapter();
        historyRV=findViewById(R.id.history_RV);
        historyRV.setAdapter(mHistoryAdapter);
        historyRV.setLayoutManager(new LinearLayoutManager(this));
        historyRV.setHasFixedSize(true);

//        mLoadingIndicatorPB=findViewById(R.id.pb_loading_indicator);
        doGitHubSearch();
        Log.d(TAG, "inHistroy" );

    }



    private void doGitHubSearch() {
        String url = HistoryUtils.buildHistoryListSearchURL(mID);
        Log.d(TAG, "querying url: " + url);
        new HistorySearchTask().execute(url);
    }

    public class HistorySearchTask extends AsyncTask<String, Void, ArrayList<MatchInfo> > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            mLoadingIndicatorPB.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<MatchInfo> doInBackground(String... strings) {
            String url = strings[0];
            String searchResults = null;
            try {
                searchResults = NetworkUtils.doHttpGet(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<MatchReferenceDto> searchResultsList = HistoryUtils.parseHistoryListResults(searchResults);
            ArrayList<MatchInfo> matchInfos = new ArrayList();
                for(MatchReferenceDto info:searchResultsList){
                    String url2 = HistoryUtils.buildOneMatchURL(info.gameId);
                    String matchResults = null;

                    try {
                        matchResults = NetworkUtils.doHttpGet(url2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    assert matchInfos != null;
                    matchInfos.add(HistoryUtils.parseOneMatchResults(matchResults));
                    Log.d(TAG, String.valueOf(matchInfos.get(0).champ));
                }
            return matchInfos;
        }

        @Override
        protected void onPostExecute(ArrayList<MatchInfo>  s) {
            super.onPostExecute(s);
            history.setVisibility(View.INVISIBLE);
            mHistoryAdapter.updateMatchinfo(s);
//            if (s != null) {
//                mErrorMessageTV.setVisibility(View.INVISIBLE);
//                mForecastListRV.setVisibility(View.VISIBLE);
//                ArrayList<WeatherRepo> searchResultsList = WeatherUtils.parseGitHubSearchResults(s);
//                mForecastAdapter.updateSearchResults(searchResultsList);
//            } else {
//                mErrorMessageTV.setVisibility(View.VISIBLE);
//                mForecastListRV.setVisibility(View.INVISIBLE);
//            }
        }
    }
}
