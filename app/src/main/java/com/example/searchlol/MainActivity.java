package com.example.searchlol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.searchlol.data.SummonerSearchRepository;
import com.example.searchlol.dataclass.SummonerClass;

import com.example.searchlol.adapter.SummonerSearchAdapter;
import com.example.searchlol.utils.HistoryUtils;
import com.example.searchlol.utils.RiotSummonerUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        SummonerSearchAdapter.OnSearchResultClickListener {

    private EditText mSearchSummonerET;
    private SummonerClass summonerClass;
    public static int trigger = 0;
    static Timer myTimer = null;
    private RiotSummonerUtils overallUtils;
    private HistoryUtils matchUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSearchSummonerET = findViewById(R.id.et_summoner);

        summonerClass = new SummonerClass();

        final TextView repoError=findViewById(R.id.errorText);
        repoError.setText("Summoner Not Found, Please try again:)");

        if(trigger==2){
            repoError.setVisibility(View.VISIBLE);
            trigger=0;
        }

        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String summonerName = mSearchSummonerET.getText().toString();
                if (!TextUtils.isEmpty(summonerName)) {
                    buildURL(summonerName);
                    myTimer = new Timer();
                    myTimer.scheduleAtFixedRate(new TimerTask() {
                        public void run() {
                            if (trigger == 1) {
//                                repoError.setVisibility(View.INVISIBLE);
                                startSecondActivity(summonerClass);
                                trigger = 0;
                            }
                            else if(trigger ==2) {//reload activity to display errmsg
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        }
                    }, 500, 500);
                }
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
            case R.id.nav_search:
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
                return true;
            case R.id.nav_saved_repos:
                Intent savedReposIntent = new Intent(this, SavedSummonerActivity.class);
                startActivity(savedReposIntent);
                return false;
            case R.id.nav_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return false;
            default:
                return false;
        }
    }

    @Override
    public void onSearchResultClicked(SummonerClass repo) {
        Intent intent = new Intent(this, SummonerDetailActivity.class);
        intent.putExtra(SummonerDetailActivity.EXTRA_GITHUB_REPO, repo);
        startActivity(intent);
    }

    public void startSecondActivity(SummonerClass repo) {
        Intent intent = new Intent(this, SummonerDetailActivity.class);
        intent.putExtra(SummonerDetailActivity.EXTRA_GITHUB_REPO, repo);
        startActivity(intent);
    }

    //change preference
    public void buildURL(String name){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String notRank = getResources().getString(R.string.rank_less);
        SummonerSearchRepository summonerSearchRepository = new SummonerSearchRepository(notRank);

        overallUtils=new RiotSummonerUtils();
        matchUtils=new HistoryUtils();

        String sort = preferences.getString(
                getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_default)
        );

        overallUtils.changeRegion(sort);//change URL for overall match
        matchUtils.changeMregion(sort); //change URL for match details
        summonerSearchRepository.loadSearchResults(name);
    }

}
