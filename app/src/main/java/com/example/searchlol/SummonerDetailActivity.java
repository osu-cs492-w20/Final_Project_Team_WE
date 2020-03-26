package com.example.searchlol;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.searchlol.asynctask.ChampionInfoTask;
import com.example.searchlol.dataclass.ChampionInfo;
import com.example.searchlol.dataclass.ChampionMasteryClass;
import com.example.searchlol.dataclass.SummonerClass;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;

import com.example.searchlol.dataclass.RankClass;
import com.example.searchlol.dataclass.SummonerRepo;
import com.example.searchlol.utils.ChampionInfoUtil;
import com.example.searchlol.utils.NetworkUtils;
import com.example.searchlol.viewmodel.SavedSummonerViewModel;

public class SummonerDetailActivity extends AppCompatActivity implements View.OnClickListener,
        ChampionInfoTask.NameCallBack {

    public static final String EXTRA_GITHUB_REPO = "SummonerDetail";
    private static ChampionInfo myChampionInfo;

    public Boolean setOnce = false;
    public SummonerClass mRepo = new SummonerClass();

    public ChampionDetailActivity mAct;
    private static String myLevel;
    private static String myUsername;
    private static int myIcon;
    public static String mId;
    private static long myDate;
    private static int c1Name, c1Level, c1Points,
            c2Name, c2Level, c2Points,
            c3Name, c3Level, c3Points;
    private static ChampionMasteryClass mC1, mC2, mC3;
    private static String mChamp1, mChamp2, mChamp3,
            mChampBio1, mChampBio2, mChampBio3;
    private static String mRankMess = "";
    private static String accountId;
    private SavedSummonerViewModel savedSummonerViewModel;
    private boolean like;

    public void receiveData(SummonerClass myResult) {
        mRepo = myResult;
        myLevel = mRepo.summonerLevel;
        myUsername = mRepo.name;
        myIcon = mRepo.profileIconId;
        mId = mRepo.id;
        myDate = mRepo.revisionDate;
        accountId = mRepo.accountId;
    }

    public void receiveRank(RankClass myResult) {
        if (myResult == null) {
            mRankMess = "Un ranked";
        } else {
            mRankMess = myResult.tier + myResult.rank + " " + myResult.leaguePoints + "lp";
        }
    }

    public void receiveMaster(ChampionMasteryClass result1, ChampionMasteryClass result2, ChampionMasteryClass result3) {
        mC1 = new ChampionMasteryClass();
        mC1 = result1;
        c1Name = result1.championId;
        c1Level = result1.championLevel;
        c1Points = result1.championPoints;

        mC2 = new ChampionMasteryClass();
        mC2 = result2;
        c2Name = result2.championId;
        c2Level = result2.championLevel;
        c2Points = result2.championPoints;

        mC3 = new ChampionMasteryClass();
        mC3 = result3;
        c3Name = result3.championId;
        c3Level = result3.championLevel;
        c3Points = result3.championPoints;
    }

    public void receiveChampionName1(ChampionInfo info) {
        mChamp1 = info.name;
        mChampBio1 = info.shortBio;
    }

    public void receiveChampionName2(ChampionInfo info) {
        mChamp2 = info.name;
        mChampBio2 = info.shortBio;
    }

    public void receiveChampionName3(ChampionInfo info) {
        mChamp3 = info.name;
        mChampBio3 = info.shortBio;
    }

    public String changeDate(long unixSeconds) {
        Date date = new java.util.Date(unixSeconds);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner_detail);

        savedSummonerViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(SavedSummonerViewModel.class);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_GITHUB_REPO)) {
            TextView repoLevelTV = findViewById(R.id.tv_summoner_Level);
            repoLevelTV.setText(myLevel);
            TextView repoNameTV = findViewById(R.id.tv_summoner_name);
            repoNameTV.setText(myUsername);
            TextView repoRankTV = findViewById(R.id.tv_Rank);
            repoRankTV.setText(mRankMess);

            TextView repoFirst1TV = findViewById(R.id.tv_champ_mastery_name1);
            repoFirst1TV.setText(mChamp1);
            TextView repoFirst2TV = findViewById(R.id.tv_champ_mastery1);
            repoFirst2TV.setText(String.valueOf(c1Points));
            TextView repoFirst3TV = findViewById(R.id.tv_champ_level1);
            repoFirst3TV.setText(String.valueOf(c1Level));

            TextView repoSecond1TV = findViewById(R.id.tv_champ_mastery_name2);
            repoSecond1TV.setText(mChamp2);
            TextView repoSecond2TV = findViewById(R.id.tv_champ_mastery2);
            repoSecond2TV.setText(String.valueOf(c2Points));
            TextView repoSecond3TV = findViewById(R.id.tv_champ_level2);
            repoSecond3TV.setText(String.valueOf(c2Level));

            TextView repoThird1TV = findViewById(R.id.tv_champ_mastery_name3);
            repoThird1TV.setText(mChamp3);
            TextView repoThird2TV = findViewById(R.id.tv_champ_mastery3);
            repoThird2TV.setText(String.valueOf(c3Points));
            TextView repoThird3TV = findViewById(R.id.tv_champ_level3);
            repoThird3TV.setText(String.valueOf(c3Level));

            ImageView repoIconIV = findViewById(R.id.tv_summoner_id);
            String iconUrl = String.format("https://opgg-static.akamaized.net/images/profile_icons/profileIcon%s.jpg", String.valueOf(myIcon));
            TextView repoDateTV = findViewById(R.id.tv_Date_des);
            repoDateTV.setText(changeDate(myDate));
            Glide.with(repoIconIV.getContext()).load(iconUrl).into(repoIconIV);

            repoIconIV.setOnClickListener(this);


            ImageView championIcon1 = findViewById(R.id.iv_champ1);
            ImageView championIcon2 = findViewById(R.id.iv_champ2);
            ImageView championIcon3 = findViewById(R.id.iv_champ3);
            String champion1Url = "https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-icons/" + c1Name + ".png";
            String champion2Url = "https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-icons/" + c2Name + ".png";
            String champion3Url = "https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-icons/" + c3Name + ".png";
            Log.d("TAG", "onCreate: " + champion1Url);
            Glide.with(championIcon1.getContext()).load(champion1Url).into(championIcon1);
            Glide.with(championIcon2.getContext()).load(champion2Url).into(championIcon2);
            Glide.with(championIcon3.getContext()).load(champion3Url).into(championIcon3);

            championIcon1.setOnClickListener(this);
            championIcon2.setOnClickListener(this);
            championIcon3.setOnClickListener(this);

            Button historyButton = findViewById(R.id.search_history_button);
            historyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newIntent = new Intent(SummonerDetailActivity.this, HistoryActivity.class);
                    newIntent.putExtra("userID", accountId);
                    startActivity(newIntent);
                }

            });

        }

        savedSummonerViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(SavedSummonerViewModel.class);

        savedSummonerViewModel.getAllSummoners().observe(this, new Observer<List<SummonerRepo>>() {
            @Override
            public void onChanged(List<SummonerRepo> gitHubRepos) {
                Log.d("SQL size", String.valueOf(gitHubRepos.size()));
            }
        });

        if (savedSummonerViewModel.getSummonerByName(mId)) Log.d("66666666666", "gogogogogog ");
        Log.d("yyyyyyyyyyyyyyyyy", String.valueOf(like));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.repo_detail, menu);
        final MenuItem item = menu.findItem(R.id.action_save_favorite_summoner);
        like = savedSummonerViewModel.getSummonerByName(mId);


        Log.d("TAG", String.valueOf(like));

        if (like) {
            item.setIcon(R.drawable.ic_action_checkedfavorite);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_favorite_summoner:
                SummonerRepo repo = new SummonerRepo();
                repo.id = mId;
                if (like) {
                    savedSummonerViewModel.deleteSavedSummoner(repo);
                    item.setIcon(R.drawable.ic_action_favorite);
                    like = false;
                } else {
                    savedSummonerViewModel.insertSavedSummoner(repo);
                    if (savedSummonerViewModel.getSummonerByName(repo.id))
                        Log.d("fffffffff", "ggggggg ");

                    item.setIcon(R.drawable.ic_action_checkedfavorite);
                    like = true;

                }
            case R.id.action_view_more:


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_summoner_id:
                setOnce = !setOnce;
                break;
            case R.id.iv_champ1:
                mAct = new ChampionDetailActivity();
                mAct.receiveMaster(mC1);
                Intent sharedIntent = new Intent(this, ChampionDetailActivity.class);

                startActivity(sharedIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.iv_champ2:
                mAct = new ChampionDetailActivity();
                mAct.receiveMaster(mC2);
                Intent sharedIntent2 = new Intent(this, ChampionDetailActivity.class);
                startActivity(sharedIntent2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.iv_champ3:
                mAct = new ChampionDetailActivity();
                mAct.receiveMaster(mC3);
                Intent sharedIntent3 = new Intent(this, ChampionDetailActivity.class);
                startActivity(sharedIntent3);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

        }
    }

    @Override
    public void onNameFinished(ChampionInfo championInfo) {
        championInfo = championInfo;
    }

    public void loadChampion(ChampionInfo championInfo) {
        myChampionInfo = championInfo;
    }
}
