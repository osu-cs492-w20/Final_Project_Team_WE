package com.example.searchlol.data;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

import com.example.searchlol.dataclass.SummonerClass;

public class SavedSummonerRepository {
    private SummonerClassDao mDAO;
    private boolean in;

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

    public boolean getSummonerByName(String fullName) {
        new GetSummonerByName(mDAO).execute(fullName);
        return in;
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

    private class GetSummonerByName extends AsyncTask<String, Void, Boolean> {
        private SummonerClassDao mAsyncTaskDAO;

        GetSummonerByName(SummonerClassDao dao) {
            mAsyncTaskDAO = dao;
        }

        @Override
        protected Boolean doInBackground(String... summonerClasses) {
            if (mAsyncTaskDAO.getSummonerById(summonerClasses[0]) == null) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            in = aBoolean;
        }
    }
}