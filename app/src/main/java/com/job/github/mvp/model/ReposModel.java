package com.job.github.mvp.model;

import android.os.AsyncTask;

import com.job.github.api.GitHubApi;
import com.job.github.api.pojo.Repos;
import com.job.github.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReposModel implements ReposContractModel {

    private static final String TAG = "ReposModel";
    private final GitHubApi gitHubApi;
    private final AppDatabase appDatabase;

    public ReposModel(GitHubApi gitHubApi, AppDatabase appDatabase) {
        this.gitHubApi = gitHubApi;
        this.appDatabase = appDatabase;
    }

    @Override
    public void loadRepos(String mUserLogin, String mClientId, String mClientSecret, boolean isThereNetwork, final OnLoadRepos onLoadRepos) {
        readReposFromDbAsync(onLoadRepos);

        gitHubApi.getRepos(mUserLogin, mClientId, mClientSecret).enqueue(new Callback<List<Repos>>() {
            @Override
            public void onResponse(Call<List<Repos>> call, Response<List<Repos>> response) {
                if (response.body() != null) {
                    onLoadRepos.onSuccess(response.body());
                    saveInDbUserAsync(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Repos>> call, Throwable t) {
                onLoadRepos.onError();
            }
        });
    }

    private void readReposFromDbAsync(OnLoadRepos onLoadRepos) {
        new ReadAsyncTask(appDatabase, onLoadRepos).execute();
    }

    private static final class ReadAsyncTask extends AsyncTask<Void, Void, List<Repos>> {
        private final AppDatabase appDatabase;
        private final OnLoadRepos onLoadRepos;

        ReadAsyncTask(AppDatabase appDatabase, OnLoadRepos onLoadRepos) {
            this.appDatabase = appDatabase;
            this.onLoadRepos = onLoadRepos;
        }

        @Override
        protected List<Repos> doInBackground(Void... voids) {
            List<Repos> repos = appDatabase.reposDao().getAll();
            appDatabase.close();
            return repos;
        }

        @Override
        protected void onPostExecute(List<Repos> repos) {
            if (repos != null) {
                onLoadRepos.onSuccess(repos);
            } else {
                onLoadRepos.onError();
            }
        }
    }


    private void saveInDbUserAsync(List<Repos> body) {
        new Thread() {
            @Override
            public void run() {
                List<Integer> reposId = new ArrayList<>();
                for (Repos repos : body) {
                    reposId.add(repos.getId());
                }
                appDatabase.reposDao().removedRepos(reposId);

                appDatabase.reposDao().insertAll(body);
                appDatabase.close();
            }
        }.start();
    }
}
