package com.job.github.mvp.view.adapter;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.job.github.R;
import com.job.github.utils.UserWithImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by slavik on 6/11/18.
 */

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.FollowersHolder> {
    private static final String TAG = "FollowersAdapter";
    private final OnItemClickListener listener;
    private List<UserWithImage> data = new ArrayList<>();

    public FollowersAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(UserWithImage userWithImage);
    }

    @NonNull
    @Override
    public FollowersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_follower, parent, false);
        return new FollowersHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowersHolder holder, int position) {
        holder.bind(data.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addUser(UserWithImage user) {
        data.add(user);
        user.setIndex(data.size()-1);
        notifyItemInserted(data.size());
    }

    public void updateUserAvatar(int i) {
        Log.d(TAG, "updateUserAvatar: notifyed number : " + i);
        notifyItemChanged(i);
    }

    class FollowersHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_name) TextView mUserName;
        @BindView(R.id.user_login) TextView mUserLogin;
        @BindView(R.id.user_location) TextView mUserLocation;
        @BindView(R.id.user_image) ImageView mUserImage;
        @BindView(R.id.follower) ConstraintLayout follower;

        FollowersHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(UserWithImage user, OnItemClickListener listener) {
            if (user.getUser().getLocation() == null || user.getUser().getLocation().isEmpty()) {
                mUserLocation.setVisibility(View.GONE);
            } else {
                mUserLocation.setVisibility(View.VISIBLE);
                mUserLocation.setText(user.getUser().getLocation());
            }
            mUserLogin.setText(user.getUser().getLogin());
            mUserName.setText(user.getUser().getName() == null || user.getUser().getName().isEmpty() ? user.getUser().getLogin() : user.getUser().getName());
            mUserImage.setImageBitmap(user.getBitmap());
            follower.setOnClickListener(v -> listener.onItemClick(user));
        }
    }

}
