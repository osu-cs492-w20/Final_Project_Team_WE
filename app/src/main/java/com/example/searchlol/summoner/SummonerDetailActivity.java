package com.example.searchlol.summoner;

import android.content.Intent;
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
import com.example.searchlol.R;
import com.example.searchlol.data.ChampionInfo;
import com.example.searchlol.data.ChampionMasteryClass;
import com.example.searchlol.data.NameTask;
import com.example.searchlol.data.SummonerClass;
import com.bumptech.glide.Glide;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import com.example.searchlol.data.RankClass;

public class SummonerDetailActivity extends AppCompatActivity implements View.OnClickListener,
                                                                        NameTask.NameCallBack {
    public static final String EXTRA_GITHUB_REPO = "SummonerDetailActivity";
    private static List<ChampionInfo> championList;
    private ChampionViewModel championViewModel;
    public Boolean setOnce=false;
    public SummonerClass mRepo= new SummonerClass();
    public ChampionDetailActivity mAct;
    private static String myLevel;
    private static String myUsername;
    private static int    myIcon;
    public  static String mId;
    private static long   myDate;
    private static int c1Name,c1Level,c1Points,mLeaguePoints;
    private static int c2Name,c2Level,c2Points;
    private static int c3Name,c3Level,c3Points;
    private static ChampionMasteryClass mC1,mC2,mC3;
    private static String mRank="";
    private static String mTier="";
    private static String mRankMess="";

    public void receiveData(SummonerClass myResult){
        mRepo=myResult;
        myLevel=mRepo.summonerLevel;
        myUsername=mRepo.name;
        myIcon = mRepo.profileIconId;
        mId = mRepo.id;
        myDate = mRepo.revisionDate;
    }



    public void getJson(List<ChampionInfo> result) {
        championList = result;
    }

    public void receiveRank(RankClass myResult){
        if(myResult==null){
            mRankMess="Unranked";
        }
        else {
            mRank = myResult.rank;
            mTier = myResult.tier;
            mLeaguePoints = myResult.leaguePoints;
            mRankMess= mTier + mRank + " " + mLeaguePoints + "lp";
        }
    }

    public void receiveMaster(ChampionMasteryClass result1, ChampionMasteryClass result2, ChampionMasteryClass result3){
        mC1= new ChampionMasteryClass();
        mC1= result1;
        c1Name=result1.championId;
        c1Level=result1.championLevel;
        c1Points=result1.championPoints;

        mC2= new ChampionMasteryClass();
        mC2= result2;
        c2Name=result2.championId;
        c2Level=result2.championLevel;
        c2Points=result2.championPoints;

        mC3= new ChampionMasteryClass();
        mC3= result3;
        c3Name=result3.championId;
        c3Level=result3.championLevel;
        c3Points=result3.championPoints;

    }

    public String changeDate(long unixSeconds) {
        Date date = new java.util.Date(unixSeconds);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

//    public String getChampionById(int id) {
//        String championName = "Aatrox";
//        championViewModel.loadName(id);
////        new NameTask(url, this).execute();
////        for (int i = 0; i < championList.size(); i++) {
////            if (id == championList.get(i).key) {
////                championName = championList.get(i).id;
////            }
////        }
//        Log.d("TAG", "getChampionById: " + championName);
//        return championName;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner_detail);

        championViewModel = new ViewModelProvider(this).get(ChampionViewModel.class);
        championViewModel.getName().observe(this, new Observer<List<ChampionInfo>>() {
            @Override
            public void onChanged(List<ChampionInfo> championInfos) {
                getJson(championInfos);
            }
        });

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_GITHUB_REPO)) {
            TextView repoNameTV = findViewById(R.id.tv_summoner_name);
            repoNameTV.setText(String.format("Player: %s", myUsername));
            TextView repoLevelTV = findViewById(R.id.tv_summoner_Level);
            repoLevelTV.setText(String.format("Level: %s", myLevel));
            TextView repoRankTV = findViewById(R.id.tv_Rank);
            repoRankTV.setText("Rank: "+ mRankMess);
            TextView repoFirstTV = findViewById(R.id.tv_summoner_description);
            repoFirstTV.setText("TOP1 Champion");
            TextView repoFirst2TV = findViewById(R.id.tv_summoner_description4);
            repoFirst2TV.setText("Mastery " + c1Points);

            TextView repoSecondTV = findViewById(R.id.tv_summoner_description2);
            repoSecondTV.setText("TOP2 Champion");
            TextView repoSecond2TV = findViewById(R.id.tv_summoner_descriptio2);
            repoSecond2TV.setText("Mastery: " + c2Points);
            TextView repoThirdTV = findViewById(R.id.tv_summoner_description3);
            repoThirdTV.setText("TOP3 Champion");
            TextView repoThird2TV = findViewById(R.id.tv_summoner_descriptio3);
            repoThird2TV.setText("Mastery: " + c3Points);
            ImageView repoIconIV = findViewById(R.id.tv_summoner_id);
            String iconUrl = "https://opgg-static.akamaized.net/images/profile_icons/profileIcon" + String.valueOf(myIcon) + ".jpg";
            TextView repoDateTV = findViewById(R.id.tv_Date_des);
            repoDateTV.setText("Last Revision Date: " + changeDate(myDate));
            Glide.with(repoIconIV.getContext()).load(iconUrl).into(repoIconIV);
            ImageView repoChampIV = findViewById(R.id.iv_summoner_solo);
            ImageView repoChamp2IV = findViewById(R.id.iv_summoner_duo);
            ImageView repoChamp3IV = findViewById(R.id.iv_summoner_third);
            Button myButton= findViewById(R.id.search_history_button);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_summoner_id:
                setOnce = !setOnce;
                break;
            case R.id.iv_summoner_solo:
                mAct=new ChampionDetailActivity();
                mAct.receiveMaster(mC1);
                Intent sharedIntent=new Intent(this, ChampionDetailActivity.class);

                startActivity(sharedIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.iv_summoner_duo:
                mAct=new ChampionDetailActivity();
                mAct.receiveMaster(mC2);
                Intent sharedIntent2=new Intent(this, ChampionDetailActivity.class);
                startActivity(sharedIntent2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.iv_summoner_third:
                mAct=new ChampionDetailActivity();
                mAct.receiveMaster(mC3);
                Intent sharedIntent3=new Intent(this, ChampionDetailActivity.class);
                startActivity(sharedIntent3);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

        }
    }


    @Override
    public void onNameFinished(List<ChampionInfo> championInfo) {
        championList = championInfo;
    }
}
