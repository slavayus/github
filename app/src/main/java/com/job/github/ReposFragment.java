package com.job.github;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private RecyclerView mRecyclerView;
    private String mUserName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadUserNameFromArguments();
        downloadRepos();
    }

    private void loadUserNameFromArguments() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalArgumentException("There is no no user name in the arguments");
        } else {
            mUserName = arguments.getString(USER_NAME);
        }
    }

    private void downloadRepos() {
        App.getGitHubApi().getRepos(mUserName).enqueue(new Callback<List<ReposModel>>() {
            @Override
            public void onResponse(Call<List<ReposModel>> call, Response<List<ReposModel>> response) {
                mRecyclerView.setAdapter(new ReposAdapter(response.body()));
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
            holder.bind(mData.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }

    private class ReposHolder extends RecyclerView.ViewHolder {
        private final TextView mTextView;

        ReposHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_repo, parent, false));
            mTextView = itemView.findViewById(R.id.repo_name);
        }

        void bind(String repoName) {
            mTextView.setText(repoName);
        }
    }


    public static ReposFragment newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString(USER_NAME, name);

        ReposFragment reposFragment = new ReposFragment();
        reposFragment.setArguments(bundle);
        return reposFragment;
    }

}
