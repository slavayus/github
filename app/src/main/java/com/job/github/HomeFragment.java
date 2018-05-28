package com.job.github;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.job.github.api.App;
import com.job.github.models.UserModel;

import java.io.InputStream;
import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private static final String TOKEN = "token";
    private static final Integer IMAGE_LOADED = 0;
    private String mToken;
    private LoadImageHandler mHandler;
    private Toolbar mToolbar;
    private ImageView mUserAvatar;
    private OnUserGet mOnUserGetListener;

    public interface OnUserGet {
        void onUserGet(UserModel user);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mOnUserGetListener = (OnUserGet) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnUserGet");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadTokenFromArguments();
        mHandler = new LoadImageHandler(HomeFragment.this);
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

        mToolbar = view.findViewById(R.id.toolbar);
        mUserAvatar = view.findViewById(R.id.user_avatar);

        App.getGitHubApi().getUser(mToken).enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.body() == null) {
                    showErrorDialog();
                } else {
                    updateToolbarText(response.body().getName());
                    mOnUserGetListener.onUserGet(response.body());
                    if (getActivity() != null) {
                        downloadAvatar(response.body().getAvatarUrl());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                showErrorDialog();
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

    private void showErrorDialog() {
        if (getActivity() == null) {
            return;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.user_home_dialog_error_title)
                .setMessage(R.string.user_home_dialog_error_message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finishAffinity();
                    }
                })
                .setCancelable(false)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void downloadAvatar(final String avatarUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream in = new java.net.URL(avatarUrl).openStream();
                    Message message = mHandler.obtainMessage(IMAGE_LOADED, BitmapFactory.decodeStream(in));
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static class LoadImageHandler extends Handler {
        private final WeakReference<HomeFragment> mActivity;

        LoadImageHandler(HomeFragment fragment) {
            mActivity = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            HomeFragment activity = mActivity.get();
            if (activity != null) {
                if (msg.what == IMAGE_LOADED) {
                    Bitmap image = (Bitmap) msg.obj;
                    activity.mUserAvatar.setImageBitmap(image);
                }
            }
        }
    }

    private void updateToolbarText(String name) {
        mToolbar.setTitle(name);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(mToolbar);
        }
    }

    public static HomeFragment newInstance(String token) {
        Bundle bundle = new Bundle();
        bundle.putString(TOKEN, token);

        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

}
