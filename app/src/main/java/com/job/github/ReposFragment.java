package com.job.github;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.job.github.api.App;
import com.job.github.models.ReposModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by slavik on 5/28/18.
 */

public class ReposFragment extends Fragment {
    private static final String USER_NAME = "USER_NAME";
    private static final String TAG = "ReposFragment";
    private static String CLIENT_ID = "CLIENT_ID";
    private static String CLIENT_SECRET = "CLIENT_SECRET";
    private RecyclerView mRecyclerView;
    private String mUserLogin;
    private String clientId;
    private String clientSecret;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadDataFromArguments();
        downloadRepos();
        Log.d(TAG, "onCreate: created repos fragment");
    }

    private void loadDataFromArguments() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalArgumentException("There is no user name in the arguments");
        } else {
            mUserLogin = arguments.getString(USER_NAME);
            clientId = arguments.getString(CLIENT_ID);
            clientSecret = arguments.getString(CLIENT_SECRET);
        }
    }

    private void downloadRepos() {
        if (mUserLogin == null) {
            return;
        }
        App.getGitHubApi().getRepos(mUserLogin, clientId, clientSecret).enqueue(new Callback<List<ReposModel>>() {
            @Override
            public void onResponse(Call<List<ReposModel>> call, Response<List<ReposModel>> response) {
                if (response.body() != null) {
                    mRecyclerView.setAdapter(new ReposAdapter(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<ReposModel>> call, Throwable t) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repos, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_repos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private class ReposAdapter extends RecyclerView.Adapter<ReposHolder> {
        private List<ReposModel> mData;

        ReposAdapter(List<ReposModel> data) {
            this.mData = data;
        }

        @NonNull
        @Override
        public ReposHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ReposHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ReposHolder holder, int position) {
            holder.bind(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    private class ReposHolder extends RecyclerView.ViewHolder {
        private final TextView mRepoName;
        private final TextView mRepoDescription;
        private final TextView mRepoLanguage;
        private final TextView mRepoLicense;

        ReposHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_repo, parent, false));
            mRepoName = itemView.findViewById(R.id.repo_name);
            mRepoDescription = itemView.findViewById(R.id.repo_description);
            mRepoLanguage = itemView.findViewById(R.id.repo_language);
            mRepoLicense = itemView.findViewById(R.id.repo_license);
        }

        void bind(ReposModel repoName) {
            mRepoName.setText(repoName.getName());
            mRepoDescription.setText(repoName.getDescription());
            mRepoLanguage.setText(repoName.getLanguage());
            if (repoName.getLicenseModel() != null) {
                mRepoLicense.setText(repoName.getLicenseModel().getName());
            }
        }
    }


    public static ReposFragment newInstance(String name, String clientId, String clientSecret) {
        Bundle bundle = new Bundle();
        bundle.putString(USER_NAME, name);
        bundle.putString(CLIENT_ID, clientId);
        bundle.putString(CLIENT_SECRET, clientSecret);

        ReposFragment reposFragment = new ReposFragment();
        reposFragment.setArguments(bundle);
        return reposFragment;
    }

}
