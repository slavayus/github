package com.job.github.mvp.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.job.github.R;
import com.job.github.api.pojo.Repos;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by slavik on 6/11/18.
 */

public class FollowersAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class FollowersHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_name) TextView mUserName;
        @BindView(R.id.user_login) TextView mUserLogin;
        @BindView(R.id.user_location) TextView mUserLocation;

        FollowersHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(Repos repos) {
        }
    }

}
