package com.example.searchlol.summoner;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.searchlol.R;
import  com.example.searchlol.data.SummonerClass;

import java.util.List;

import static com.example.searchlol.utils.RiotSummonerUtils.mLevel;
import static com.example.searchlol.utils.RiotSummonerUtils.mUsername;
import static com.example.searchlol.utils.RiotSummonerUtils.mId;
import static com.example.searchlol.utils.RiotSummonerUtils.mIcon;

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
            TextView repoRankTV = findViewById(R.id.tv_Rank);
            repoRankTV.setText("Default: IronV");
            TextView repoLevelTV = findViewById(R.id.tv_summoner_Level);
            repoLevelTV.setText("Level "+mLevel);
            ImageView repoIconTV = findViewById(R.id.tv_summoner_id);//uicon ...textview iconveiw
            //repoIconTV.setImageIcon();
            TextView repoDescriptionTV = findViewById(R.id.tv_summoner_description);
            repoDescriptionTV.setText("About: This player is BronzeV");

            //TextView repoStarsTV = findViewById(R.id.tv_repo_stars);
            //repoStarsTV.setText(Integer.toString(mRepo.stargazers_count));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.repo_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_repo:
                //invoke activity
                return true;
            case R.id.action_share:
                //invoke activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
