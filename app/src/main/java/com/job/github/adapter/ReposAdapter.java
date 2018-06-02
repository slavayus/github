package com.job.github.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.job.github.R;
import com.job.github.pojo.Repos;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposHolder> {
    private List<Repos> mData = new ArrayList<>();

    @NonNull
    @Override
    public ReposHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_repo, parent, false);
        return new ReposHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReposHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Repos> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    class ReposHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.repo_name) TextView mRepoName;
        @BindView(R.id.repo_description) TextView mRepoDescription;
        @BindView(R.id.repo_language) TextView mRepoLanguage;
        @BindView(R.id.repo_license) TextView mRepoLicense;
        @BindView(R.id.repo_stars) TextView mRepoStars;

        ReposHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(Repos repos) {
            mRepoName.setText(repos.getName());

            if (repos.getDescription() == null) {
                mRepoDescription.setVisibility(View.GONE);
            } else {
                mRepoDescription.setVisibility(View.VISIBLE);
                mRepoDescription.setText(repos.getDescription());
            }

            mRepoLanguage.setText(repos.getLanguage());
            mRepoLanguage.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_language_color_shape, 0, 0, 0);

            if (repos.getStargazersCount() == 0) {
                mRepoStars.setVisibility(View.GONE);
            } else {
                mRepoStars.setVisibility(View.VISIBLE);
                mRepoStars.setText(String.valueOf(repos.getStargazersCount()));
            }

            if (repos.getLicense() == null) {
                mRepoLicense.setVisibility(View.GONE);
            } else {
                mRepoLicense.setVisibility(View.VISIBLE);
                mRepoLicense.setText(repos.getLicense().getName());
            }
        }
    }

}
