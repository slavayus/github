package com.job.github;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.job.github.model.HomeContractModel;
import com.job.github.model.HomeModel;
import com.job.github.pojo.User;
import com.job.github.presenter.HomeContractView;
import com.job.github.presenter.HomePresenter;

public class HomeFragment extends Fragment implements HomeContractView {
    private static final String TAG = "HomeFragment";
    private static final String TOKEN = "token";
    private String mToken;
    private Toolbar mToolbar;
    private ImageView mUserAvatar;
    private OnUserGet mOnUserGetListener;
    private ProgressDialog dialog;

    public interface OnUserGet {
        void onUserGet(User user);
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
    }

    @Override
    public void showProgressDialog() {
        dialog = new ProgressDialog(this.getContext());
        dialog.setMessage(getResources().getString(R.string.loading_user_data));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public String getToken() {
        return mToken;
    }

    @Override
    public void stopProgressDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onUserGet(User data) {
        mOnUserGetListener.onUserGet(data);
    }

    @Override
    public void showUserAvatar(Bitmap image) {
        mUserAvatar.setImageBitmap(image);
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
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        mToolbar = view.findViewById(R.id.toolbar);
        mUserAvatar = view.findViewById(R.id.user_avatar);

        TextView textView = view.findViewById(R.id.token);
        textView.setText(mToken);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> Snackbar.make(view1, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        HomeContractModel homeModel = new HomeModel();
        HomePresenter mPresenter = new HomePresenter(homeModel);
        mPresenter.attachView(this);
        mPresenter.viewIsReady();

        return view;
    }

    @Override
    public void showErrorDialog() {
        if (getActivity() == null) {
            return;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.user_home_dialog_error_title)
                .setMessage(R.string.user_home_dialog_error_message)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> getActivity().finishAffinity())
                .setCancelable(false)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void updateToolbarText(String name) {
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
