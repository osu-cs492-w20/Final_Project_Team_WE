package com.example.searchlol.summoner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;

import com.example.searchlol.R;
import com.example.searchlol.data.SummonerClass;
import com.example.searchlol.data.SummonerRepo;
import com.example.searchlol.utils.RiotSummonerUtils;

public class SavedSummonerActivity extends AppCompatActivity implements SummonerSearchAdapter.OnSearchResultClickListener{
    private SavedSummonerViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_saved_repos);

        //RecyclerView savedReposRV = findViewById(R.id.rv_saved_repos);
       // savedReposRV.setLayoutManager(new LinearLayoutManager(this));
        //savedReposRV.setHasFixedSize(true);

        final SummonerSearchAdapter adapter = new SummonerSearchAdapter(this);
       // savedReposRV.setAdapter(adapter);

        mViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(SavedSummonerViewModel.class);

        mViewModel.getAllSummoners().observe(this, new Observer<List<SummonerRepo>>() {
            @Override
            public void onChanged(List<SummonerRepo> gitHubRepos) {
//                adapter.updateSearchResults(gitHubRepos);
            }
        });
    }

    @Override
    public void onSearchResultClicked(SummonerClass repo) {
        Intent intent = new Intent(this, SummonerDetailActivity.class);
        intent.putExtra(SummonerDetailActivity.EXTRA_GITHUB_REPO, repo);
        startActivity(intent);
    }
}