package com.example.searchlol.summoner;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.searchlol.R;
import com.example.searchlol.data.ChampionMasteryClass;

public class ChampionDetailActivity extends AppCompatActivity {
    private boolean shouldAllowBack=false;
    private static int c1Name,c1Level,c1Points;
    private static int c2Name,c2Level,c2Points;
    private static int c3Name,c3Level,c3Points;

    public void receiveMaster(ChampionMasteryClass result1, ChampionMasteryClass result2, ChampionMasteryClass result3){
        c1Name=result1.championId;
        c1Level=result1.championLevel;
        c1Points=result1.championPoints;

        c2Name=result2.championId;
        c2Level=result2.championLevel;
        c2Points=result2.championPoints;

        c3Name=result3.championId;
        c3Level=result3.championLevel;
        c3Points=result3.championPoints;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another_scene);

        TextView repomLevelTV = findViewById(R.id.tv_champ_Level);
        repomLevelTV.setText("Level: " + c1Level);

        TextView repoSecondTV = findViewById(R.id.tv_champ_mastery);
        repoSecondTV.setText("Mastery: "+ c1Points);

    }



    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
