package com.example.searchlol.summoner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.searchlol.R;
import com.example.searchlol.data.ChampionMasteryClass;
import  com.example.searchlol.data.SummonerClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;


public class SummonerDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_GITHUB_REPO = "SummonerDetailActivity";
    public Boolean setOnce=false;
    public SummonerClass mRepo= new SummonerClass();
    private static String myLevel;
    private static String myUsername;
    private static int    myIcon;
    public  static String mId;
    private static long   myDate;
    private static int c1Name,c1Level,c1Points;
    private static int c2Name,c2Level,c2Points;
    private static int c3Name,c3Level,c3Points;

    public void receiveData(SummonerClass myResult){
        mRepo=myResult;
        myLevel=mRepo.summonerLevel;
        myUsername=mRepo.name;
        myIcon = mRepo.profileIconId;
        mId = mRepo.id;
        myDate = mRepo.revisionDate;
    }

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

    public String changeDate(long unixSeconds){
        Date date = new java.util.Date(unixSeconds);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner_detail);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_GITHUB_REPO)) {
            TextView repoNameTV = findViewById(R.id.tv_summoner_name);
            repoNameTV.setText(String.format("Player: %s", myUsername));
            TextView repoLevelTV = findViewById(R.id.tv_summoner_Level);
            repoLevelTV.setText(String.format("Level: %s", myLevel));
            TextView repoRankTV = findViewById(R.id.tv_Rank);
            repoRankTV.setText("Default: IronV");
            TextView repoFirstTV = findViewById(R.id.tv_summoner_description);
            repoFirstTV.setText("TOP1 Champ: "+ c1Level);
            TextView repoFirst2TV = findViewById(R.id.tv_summoner_descriptio);
            repoFirst2TV.setText("Mastery "+ c1Points);
            TextView repoSecondTV = findViewById(R.id.tv_summoner_description2);
            repoSecondTV.setText("TOP2 Champ" + c2Level);
            TextView repoSecond2TV = findViewById(R.id.tv_summoner_descriptio2);
            repoSecond2TV.setText("Mastery: "+ c2Points);
            TextView repoThirdTV = findViewById(R.id.tv_summoner_description3);
            repoThirdTV.setText("TOP3 Champ" + c3Level);
            TextView repoThird2TV = findViewById(R.id.tv_summoner_descriptio3);
            repoThird2TV.setText("Mastery: " + c3Name);
            ImageView repoIconIV = findViewById(R.id.tv_summoner_id);
            String iconUrl = "https://opgg-static.akamaized.net/images/profile_icons/profileIcon" + String.valueOf(myIcon) + ".jpg";
            TextView repoDateTV = findViewById(R.id.tv_Date_des);
            repoDateTV.setText("Last Revision Date: "+  changeDate(myDate));
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
