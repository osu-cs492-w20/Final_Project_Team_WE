package com.example.searchlol;

import android.content.Intent;
import android.content.res.Resources;
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
import com.example.searchlol.data.ChampionRepository;
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
import com.example.searchlol.viewmodel.ChampionViewModel;
import com.example.searchlol.viewmodel.SavedSummonerViewModel;

public class SummonerDetailActivity extends AppCompatActivity implements View.OnClickListener,
        ChampionInfoTask.NameCallBack {
    public static final String EXTRA_GITHUB_REPO = "SummonerDetailActivity";
    private static ChampionInfo myChampionInfo;
    private ChampionViewModel championViewModel;

    public Boolean setOnce = false;
    public SummonerClass mRepo = new SummonerClass();

    public ChampionDetailActivity mAct;
    private static String myLevel;
    private static String myUsername;
    private static int myIcon;
    public static String mId;
    private static long myDate;
    private static int c1Name, c1Level, c1Points, mLeaguePoints;
    private static int c2Name, c2Level, c2Points;
    private static int c3Name, c3Level, c3Points;
    private static ChampionMasteryClass mC1, mC2, mC3;
    private static String mRank = "";
    private static String mTier = "";
    private static String mRankMess = "";
    private static String accountId;
    private Button historyButton;
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

    public void getJson(ChampionInfo result) {
        myChampionInfo = result;
    }

    public void receiveRank(RankClass myResult) {
        if (myResult == null) {
            mRankMess = "Un ranked";
        } else {
            mRank = myResult.rank;
            mTier = myResult.tier;
            mLeaguePoints = myResult.leaguePoints;
            mRankMess = mTier + mRank + " " + mLeaguePoints + "lp";
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

    public String changeDate(long unixSeconds) {
        Date date = new java.util.Date(unixSeconds);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner_detail);

        String champ1URL = ChampionInfoUtil.buildChampionInfoURL(String.valueOf(c1Name));
        String champ2URL = ChampionInfoUtil.buildChampionInfoURL(String.valueOf(c2Name));
        String champ3URL = ChampionInfoUtil.buildChampionInfoURL(String.valueOf(c3Name));


        new ChampionTask(champ1URL).execute();
        new ChampionTask(champ2URL).execute();
        new ChampionTask(champ3URL).execute();


        championViewModel = new ViewModelProvider(this).get(ChampionViewModel.class);

        championViewModel.getName().observe(this, new Observer<ChampionInfo>() {
            @Override
            public void onChanged(ChampionInfo championInfo) {
                getJson(championInfo);
            }
        });

        savedSummonerViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(SavedSummonerViewModel.class);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_GITHUB_REPO)) {
            TextView repoLevelTV = findViewById(R.id.tv_summoner_Level);
            repoLevelTV.setText(String.format(myLevel));
            TextView repoNameTV = findViewById(R.id.tv_summoner_name);
            repoNameTV.setText(String.format(myUsername));
            TextView repoRankTV = findViewById(R.id.tv_Rank);
            repoRankTV.setText(mRankMess);
            TextView repoFirstTV = findViewById(R.id.tv_summoner_description);
            repoFirstTV.setText(R.string.top_1);
            TextView repoFirst2TV = findViewById(R.id.tv_summoner_description4);
            repoFirst2TV.setText(String.format("%s%d", getString(R.string.mastery_prompt), c1Points));

            TextView repoSecondTV = findViewById(R.id.tv_summoner_description2);
            repoSecondTV.setText(R.string.top_2);
            TextView repoSecond2TV = findViewById(R.id.tv_summoner_descriptio2);
            repoSecond2TV.setText(String.format("%s%d", getString(R.string.mastery_prompt), c2Points));

            TextView repoThirdTV = findViewById(R.id.tv_summoner_description3);
            repoThirdTV.setText(R.string.top_3);
            TextView repoThird2TV = findViewById(R.id.tv_summoner_descriptio3);
            repoThird2TV.setText(String.format("%s%d", getString(R.string.mastery_prompt), c3Points));

            ImageView repoIconIV = findViewById(R.id.tv_summoner_id);
            String iconUrl = "https://opgg-static.akamaized.net/images/profile_icons/profileIcon" + String.valueOf(myIcon) + ".jpg";
            TextView repoDateTV = findViewById(R.id.tv_Date_des);
            repoDateTV.setText("Last Revision Date: " + changeDate(myDate));
            Glide.with(repoIconIV.getContext()).load(iconUrl).into(repoIconIV);
            ImageView repoChampIV = findViewById(R.id.iv_summoner_solo);
            ImageView repoChamp2IV = findViewById(R.id.iv_summoner_duo);
            ImageView repoChamp3IV = findViewById(R.id.iv_summoner_third);
            Button myButton = findViewById(R.id.search_history_button);
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            repoIconIV.setOnClickListener(this);


            ImageView championIcon1 = findViewById(R.id.iv_summoner_solo);
            ImageView championIcon2 = findViewById(R.id.iv_summoner_duo);
            ImageView championIcon3 = findViewById(R.id.iv_summoner_third);
            String champion1Url = "https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-icons/" + c1Name + ".png";
            String champion2Url = "https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-icons/" + c2Name + ".png";
            String champion3Url = "https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-icons/" + c3Name + ".png";
            Log.d("TAG", "onCreate: " + champion1Url);
            Glide.with(championIcon1.getContext()).load(champion1Url).into(championIcon1);
            Glide.with(championIcon2.getContext()).load(champion2Url).into(championIcon2);
            Glide.with(championIcon3.getContext()).load(champion3Url).into(championIcon3);

            repoChampIV.setOnClickListener(this);
            repoChamp2IV.setOnClickListener(this);
            repoChamp3IV.setOnClickListener(this);

            historyButton = findViewById(R.id.search_history_button);
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
            case R.id.iv_summoner_solo:
                mAct = new ChampionDetailActivity();
                mAct.receiveMaster(mC1);
                Intent sharedIntent = new Intent(this, ChampionDetailActivity.class);

                startActivity(sharedIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.iv_summoner_duo:
                mAct = new ChampionDetailActivity();
                mAct.receiveMaster(mC2);
                Intent sharedIntent2 = new Intent(this, ChampionDetailActivity.class);
                startActivity(sharedIntent2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.iv_summoner_third:
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

    public class ChampionTask extends AsyncTask<String, Void, String> {
        private String champURL;

        public ChampionTask(String url){
            champURL = url;
        }

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            try {
                result = NetworkUtils.doHttpGet(champURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            ChampionInfo result = ChampionInfoUtil.parseChampionInfo(s);
            loadChampion(result);
            Log.d("TAG", "onCreate championInfo: " + myChampionInfo.name + "\n" + myChampionInfo.shortBio);
        }
    }
}
