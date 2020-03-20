package com.example.searchlol.data;
import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
public class SavedSummonerRepository {
    private SummonerClassDao mDAO;

    public SavedSummonerRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mDAO = db.summonerClassDao();
    }
    public void insertSavedSummoner(SummonerClass summoner) {
        new InsertAsyncTask(mDAO).execute(summoner);
    }

    public void deleteSavedSummoner(SummonerClass summoner) {
        new DeleteAsyncTask(mDAO).execute(summoner);
    }
    public LiveData<List<SummonerClass>> getAllSummoners() {
        return mDAO.getAllSummoners();
    }

    public LiveData<SummonerClass> getSummonerByName(String fullName) {
        return mDAO.getSummonerByName(fullName);
    }
    private static class InsertAsyncTask extends AsyncTask<SummonerClass, Void, Void> {
        private SummonerClassDao mAsyncTaskDAO;
        InsertAsyncTask(SummonerClassDao dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(SummonerClass... summonerClasses) {
            mAsyncTaskDAO.insert(summonerClasses[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<SummonerClass, Void, Void> {
        private SummonerClassDao mAsyncTaskDAO;
        DeleteAsyncTask(SummonerClassDao dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(SummonerClass... summonerClasses) {
            mAsyncTaskDAO.delete(summonerClasses[0]);
            return null;
        }
    }
}