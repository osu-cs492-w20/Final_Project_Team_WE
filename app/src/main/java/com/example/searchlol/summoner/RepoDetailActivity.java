package com.example.searchlol.summoner;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.searchlol.R;
import  com.example.searchlol.data.SummonerClass;
import com.example.searchlol.utils.RiotSummonerUtils;

import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import static com.example.searchlol.utils.RiotSummonerUtils.dataParsed;
import static com.example.searchlol.utils.RiotSummonerUtils.singleParsed;

public class RepoDetailActivity extends AppCompatActivity {
    public static final String EXTRA_GITHUB_REPO = "RepodetailActivity";
    private List<SummonerClass> mSearchResultsList;

    public void updateSearchResults(List<SummonerClass> searchResultsList, String a) {
        mSearchResultsList = searchResultsList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner_detail);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_GITHUB_REPO)) {
            TextView repoNameTV = findViewById(R.id.tv_summoner_name);
            repoNameTV.setText(dataParsed);
            TextView repoDescriptionTV = findViewById(R.id.tv_summoner_description);
            repoDescriptionTV.setText(singleParsed);

            //TextView repoStarsTV = findViewById(R.id.tv_repo_stars);
            //repoStarsTV.setText(Integer.toString(mRepo.stargazers_count));
        }
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu., menu);
        return true;
    }

     */
    /*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_repo:
                viewRepoOnWeb();
                return true;
            case R.id.action_share:
                shareRepo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void viewRepoOnWeb() {
        if (mRepo != null) {
            Uri repoUri = Uri.parse(mRepo.html_url);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, repoUri);

            PackageManager pm = getPackageManager();
            List<ResolveInfo> activities = pm.queryIntentActivities(webIntent, PackageManager.MATCH_DEFAULT_ONLY);
            if (activities.size() > 0) {
                startActivity(webIntent);
            }
        }
    }

    private void shareRepo() {
        if (mRepo != null) {
            String shareText = getString(R.string.share_repo_text, mRepo.full_name, mRepo.html_url);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            shareIntent.setType("text/plain");

            Intent chooserIntent = Intent.createChooser(shareIntent, null);
            startActivity(chooserIntent);
        }
        */




}
