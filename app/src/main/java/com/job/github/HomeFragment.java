package com.job.github;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.job.github.api.App;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private static final String TOKEN = "token";
    private String mToken;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadTokenFromArguments();
    }

    private void loadTokenFromArguments() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalArgumentException("There is no token in arguments");
        } else {
            mToken = arguments.getString(TOKEN);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_home, container, false);


        App.getGitHubApi().getUser(mToken).enqueue(new Callback<com.job.github.UserModel>() {
            @Override
            public void onResponse(Call<com.job.github.UserModel> call, Response<com.job.github.UserModel> response) {
                Log.d(TAG, "onResponse: " + response.body().getName());
                Toolbar toolbar = view.findViewById(R.id.toolbar);
                toolbar.setTitle(response.body().getName());
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                if (activity != null) {
                    activity.setSupportActionBar(toolbar);
                }
            }

            @Override
            public void onFailure(Call<com.job.github.UserModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });




        TextView textView = view.findViewById(R.id.token);
        textView.setText(mToken);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return view;
    }

    public static HomeFragment newInstance(String token) {
        Bundle bundle = new Bundle();
        bundle.putString(TOKEN, token);

        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

}
