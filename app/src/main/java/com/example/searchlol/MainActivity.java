package com.example.searchlol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.searchlol.data.Status;
import com.example.searchlol.data.SummonerClass;
import com.example.searchlol.summoner.SummonerDetailActivity;
import com.example.searchlol.summoner.SummonerSearchAdapter;
import com.example.searchlol.summoner.SummonerSearchViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static com.example.searchlol.summoner.ChampionAsyncTask.trigger;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
                                                    SummonerSearchAdapter.OnSearchResultClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private EditText mSearchSummonerET;
    private RecyclerView mSearchResultsRV;
    private SummonerClass summonerClass;
    private SummonerSearchViewModel mViewModel;
    private ProgressBar mLoadingIndicatorPB;
    public static int trigger=0;
    static Timer myTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mSearchSummonerET = findViewById(R.id.et_summoner);
        mSearchResultsRV = findViewById(R.id.rv_search_results);

        mSearchResultsRV.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultsRV.setHasFixedSize(true);

        SummonerSearchAdapter mSearchResultAdapter = new SummonerSearchAdapter(this);
        mSearchResultsRV.setAdapter(mSearchResultAdapter);
        mLoadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        mViewModel = new SummonerSearchViewModel();
        summonerClass = new SummonerClass();

        mViewModel.getLoadingStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {
                if (status == Status.SUCCESS) {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mSearchResultsRV.setVisibility(View.VISIBLE);
                } else if (status == Status.LOADING) {
                    mLoadingIndicatorPB.setVisibility(View.VISIBLE);
                }
                else {
                    mLoadingIndicatorPB.setVisibility(View.INVISIBLE);
                    mSearchResultsRV.setVisibility(View.INVISIBLE);
                }
            }
        });

        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String summonerName = mSearchSummonerET.getText().toString();
                if (!TextUtils.isEmpty(summonerName)) {
                    mViewModel.loadSearchResults(summonerName);
                        myTimer = new Timer();
                        myTimer.scheduleAtFixedRate(new TimerTask() {

                            public void run() {
                                if(trigger==1) {
                                    startSecondActivity(summonerClass);
                                    trigger=0;
                                }

                            }
                        }, 1000, 1000);

                }

            }
        });



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.drawer_menu);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        mDrawerLayout.closeDrawers();
        switch (menuItem.getItemId()) {
            case R.id.nav_search:
                return true;
            case R.id.action_save_favorite_summoner:
                //invoke activity
                return true;
//            case R.id.nav_saved_repos:
//                Intent savedReposIntent = new Intent(this, SavedReposActivity.class);
//                startActivity(savedReposIntent);
//                return true;
//            case R.id.nav_settings:
//                Intent settingsIntent = new Intent(this, SettingsActivity.class);
//                startActivity(settingsIntent);
//                return true;
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

    public void startSecondActivity(SummonerClass repo){
        Intent intent = new Intent(this, SummonerDetailActivity.class);
        intent.putExtra(SummonerDetailActivity.EXTRA_GITHUB_REPO, repo);
        startActivity(intent);
    }

    public void startTimer() {
        if (myTimer == null) {
            myTimer = new Timer();
            myTimer.scheduleAtFixedRate(new TimerTask() {

                public void run() {

                }
            }, 1000, 1000);
        }
    }

    private void doGitHubSearch(String searchQuery) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        /*
        String sort = preferences.getString(
                getString(R.string.pref_sort_key),
                getString(R.string.pref_sort_default)
        );
        String language = preferences.getString(
                getString(R.string.pref_language_key),
                getString(R.string.pref_language_default)
        );
        String user = preferences.getString(
                getString(R.string.pref_user_key), ""
        );
        boolean searchInName = preferences.getBoolean(
                getString(R.string.pref_in_name_key), true
        );
        boolean searchInDescription = preferences.getBoolean(
                getString(R.string.pref_in_description_key), true
        );
        boolean searchInReadme = preferences.getBoolean(
                getString(R.string.pref_in_readme_key), true
        );
        */
        mViewModel.loadSearchResults(searchQuery);
    }

}
