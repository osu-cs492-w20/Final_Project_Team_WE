package com.example.searchlol.summoner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.searchlol.R;
import  com.example.searchlol.data.SummonerClass;

import java.util.List;

import static com.example.searchlol.utils.RiotSummonerUtils.mLevel;
import static com.example.searchlol.utils.RiotSummonerUtils.mUsername;
import static com.example.searchlol.utils.RiotSummonerUtils.mId;

public class RepoDetailActivity extends AppCompatActivity {
    public static final String EXTRA_GITHUB_REPO = "RepodetailActivity";
    private List<SummonerClass> mSearchResultsList;

    public void updateSearchResults(List<SummonerClass> searchResultsList, String a) {
        mSearchResultsList = searchResultsList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner_detail);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_GITHUB_REPO)) {
            TextView repoNameTV = findViewById(R.id.tv_summoner_name);
            repoNameTV.setText("Player: "+mUsername);
            TextView repoLevelTV = findViewById(R.id.tv_summoner_Level);
            repoLevelTV.setText("Level "+mLevel);
            TextView repoIconTV = findViewById(R.id.tv_summoner_id);
            repoIconTV.setText("ICON");
            TextView repoDescriptionTV = findViewById(R.id.tv_summoner_description);
            repoDescriptionTV.setText("About: This player is BronzeV");

            //TextView repoStarsTV = findViewById(R.id.tv_repo_stars);
            //repoStarsTV.setText(Integer.toString(mRepo.stargazers_count));
        }
    }

}
