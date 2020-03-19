package com.example.searchlol.summoner;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.searchlol.R;

public class ChampionDetailActivity extends AppCompatActivity {
    private boolean shouldAllowBack=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.another_scene);

        TextView repomLevelTV = findViewById(R.id.tv_champ_Level);
        repomLevelTV.setText("Level: 7");

        TextView repoSecondTV = findViewById(R.id.tv_champ_mastery);
        repoSecondTV.setText("Mastery: 78971");


    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
