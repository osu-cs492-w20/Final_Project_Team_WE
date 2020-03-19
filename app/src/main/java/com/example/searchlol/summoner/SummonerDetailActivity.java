package com.example.searchlol.summoner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Fade;
import androidx.transition.Scene;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.example.searchlol.R;
import  com.example.searchlol.data.SummonerClass;

import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.searchlol.utils.RiotSummonerUtils.mLevel;
import static com.example.searchlol.utils.RiotSummonerUtils.mUsername;
import static com.example.searchlol.utils.RiotSummonerUtils.mId;
import static com.example.searchlol.utils.RiotSummonerUtils.mIcon;

public class SummonerDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_GITHUB_REPO = "SummonerDetailActivity";
    private List<SummonerClass> mSearchResultsList;
    private ViewGroup sceneRoot;
    private ViewGroup mViewHierarchy;
    private ViewGroup sceneRoot2;
    public Boolean setOnce=false;
    Scene aScene;
    Scene anotherScene;
    Scene mScene;
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
            repoNameTV.setText(String.format("Player: %s", mUsername));
            TextView repoLevelTV = findViewById(R.id.tv_summoner_Level);
            repoLevelTV.setText(String.format("Level: %s", mLevel));
            TextView repoRankTV = findViewById(R.id.tv_Rank);
            repoRankTV.setText("Default: IronV");
            TextView repoFirstTV = findViewById(R.id.tv_summoner_description);
            repoFirstTV.setText("TOP1 Champ");
            TextView repoSecondTV = findViewById(R.id.tv_summoner_description2);
            repoSecondTV.setText("TOP2 Champ");
            TextView repoThirdTV = findViewById(R.id.tv_summoner_description3);
            repoThirdTV.setText("TOP3 Champ");
            ImageView repoIconIV = findViewById(R.id.tv_summoner_id);
            String iconUrl = "https://opgg-static.akamaized.net/images/profile_icons/profileIcon" + String.valueOf(mIcon) + ".jpg";
            Log.d(TAG,"onClick: "+ iconUrl);
            Glide.with(repoIconIV.getContext()).load(iconUrl).into(repoIconIV);
            repoIconIV.setOnClickListener(this);

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
            case R.id.action_view_more:
                //invoke activity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_summoner_id:
                Intent sharedIntent=new Intent(this, ChampionDetailActivity.class);
                startActivity(sharedIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                setOnce = !setOnce;
                break;
        }
    }


}
