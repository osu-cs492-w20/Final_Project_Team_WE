package com.example.searchlol.summoner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.searchlol.R;
import com.example.searchlol.data.ChampionMasteryClass;

import static com.example.searchlol.summoner.SummonerDetailActivity.EXTRA_GITHUB_REPO;

public class ChampionDetailActivity extends AppCompatActivity {
    public static final String TAG = "ChampionDetailActivity";
    private boolean shouldAllowBack=false;
    private static int mName,mLevel,mPoints;


    public void receiveMaster(ChampionMasteryClass result){
        mName=result.championId;
        mLevel=result.championLevel;
        mPoints=result.championPoints;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another_scene);

        Intent intent = getIntent();
        if (intent != null) {
                TextView repomLevelTV = findViewById(R.id.tv_champ_Level);
                repomLevelTV.setText("Level: " + mLevel);

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
