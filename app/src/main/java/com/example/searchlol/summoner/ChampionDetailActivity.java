package com.example.searchlol.summoner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.searchlol.R;
import com.example.searchlol.data.ChampionMasteryClass;

import static com.example.searchlol.summoner.SummonerDetailActivity.EXTRA_GITHUB_REPO;

public class ChampionDetailActivity extends AppCompatActivity {
    public static final String TAG = "ChampionDetailActivity";
    private boolean shouldAllowBack = false;
    private static int mName, mLevel, mPoints;


    public void receiveMaster(ChampionMasteryClass result) {
        mName = result.championId;
        mLevel = result.championLevel;
        mPoints = result.championPoints;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another_scene);

        Intent intent = getIntent();
        if (intent != null) {
            TextView repoLevelTV = findViewById(R.id.tv_champ_Level);
            repoLevelTV.setText("Level: " + mLevel);

            String url = "https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-icons/" + mName + ".png";

            ImageView championIcon = findViewById(R.id.iv_champ_icon);
            Glide.with(championIcon.getContext()).load(url).into(championIcon);
            Log.d(TAG, "mName is " + mName);

            TextView repoSecondTV = findViewById(R.id.tv_champ_mastery);
            repoSecondTV.setText("Mastery: " + mPoints);
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
