package com.example.searchlol;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.searchlol.data.SummonerClass;

public class SummonerDetailActivity extends AppCompatActivity {
    public static final String EXTRA_SUMMONER_DETAIL = "SummonerClass";

    private SummonerClass mSummoner;
    private boolean mIsSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner_detail);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_SUMMONER_DETAIL)) {
            mSummoner = (SummonerClass)intent.getSerializableExtra(EXTRA_SUMMONER_DETAIL);

            TextView summonerNameTV = findViewById(R.id.tv_summoner_name);
            summonerNameTV.setText(mSummoner.name);

            TextView summonerIdTV = findViewById(R.id.tv_summoner_id);
            summonerIdTV.setText(mSummoner.id);
        }

    }
}
