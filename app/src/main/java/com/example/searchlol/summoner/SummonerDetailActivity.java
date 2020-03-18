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
import androidx.transition.Scene;
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

        // Create the scene root for the scenes in this app
        mViewHierarchy = (ViewGroup) findViewById(R.id.rl_summoner_info);
        /*
        sceneRoot = (ViewGroup) findViewById(R.id.scene_root);
        aScene = Scene.getSceneForLayout(sceneRoot, R.layout.a_scene, this);
        anotherScene = Scene.getSceneForLayout(sceneRoot, R.layout.another_scene, this);
        mScene = new Scene(sceneRoot, mViewHierarchy);
        */
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
                setOnce = !setOnce;
                TransitionManager.beginDelayedTransition(mViewHierarchy);
                // Then, we can just change view properties as usual.
                View square = mViewHierarchy.findViewById(R.id.tv_summoner_id);
                ViewGroup.LayoutParams params = square.getLayoutParams();
                int newSize= getResources().getDimensionPixelSize(R.dimen.square_size_normal);
                if(setOnce) {
                    newSize = getResources().getDimensionPixelSize(R.dimen.square_size_expanded);
                }
                params.width = newSize;
                params.height = newSize;
                square.setLayoutParams(params);
                break;
        }
    }

}
