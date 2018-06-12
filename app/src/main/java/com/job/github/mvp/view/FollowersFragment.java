package com.job.github.mvp.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.job.github.R;
import com.job.github.api.pojo.User;
import com.job.github.dagger.component.DaggerFollowersFragmentComponent;
import com.job.github.mvp.presenter.FollowersFragmentContractView;
import com.job.github.mvp.presenter.FollowersFragmentPresenter;
import com.job.github.mvp.view.adapter.FollowersAdapter;
import com.job.github.utils.UserWithImage;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by slavik on 6/11/18.
 */

public class FollowersFragment extends Fragment implements FollowersFragmentContractView {
    public static final String USER_LOGIN = "USER_LOGIN";
    private static final String CLIENT_ID = "CLIENT_ID";
    private static final String CLIENT_SECRET = "CLIENT_SECRET";
    @Inject FollowersAdapter mAdapter;
    @Inject FollowersFragmentPresenter mPresenter;
    @BindView(R.id.recycler_view_fragment) RecyclerView mRecyclerView;
    private Unbinder bind;
    private String mUserLogin;
    private String mClientId;
    private String mClientSecret;
    private OnSelectFollower onSelectFollower;

    public interface OnSelectFollower {
        void onSelectFollower(User user);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onSelectFollower = (OnSelectFollower) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnSelectFollower");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readFromArguments();
    }

    private void readFromArguments() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalArgumentException("There is no user login in arguments");
        } else {
            mUserLogin = arguments.getString(USER_LOGIN);
            mClientId = arguments.getString(CLIENT_ID);
            mClientSecret = arguments.getString(CLIENT_SECRET);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers, container, false);

        bind = ButterKnife.bind(this, view);


        DaggerFollowersFragmentComponent
                .create()
                .injectFollowersFragment(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.attachView(this);
        mPresenter.viewIsReady();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.viewIsDestroyed();
        bind.unbind();
    }

    public static Fragment newInstance(String userLogin, String clientId, String clientSecret) {
        Bundle bundle = new Bundle();
        bundle.putString(USER_LOGIN, userLogin);
        bundle.putString(CLIENT_ID, clientId);
        bundle.putString(CLIENT_SECRET, clientSecret);

        FollowersFragment followersFragment = new FollowersFragment();
        followersFragment.setArguments(bundle);
        return followersFragment;
    }

    @Override
    public String getUserLogin() {
        return mUserLogin;
    }

    @Override
    public void addNewUser(UserWithImage user) {
        mAdapter.addUser(user);
    }

    @Override
    public void updateUserAvatar(int i) {
        mAdapter.updateUserAvatar(i);
    }

    @Override
    public String getClientId() {
        return mClientId;
    }

    @Override
    public String getClientSecret() {
        return mClientSecret;
    }

    @Override
    public void followerSelected(User user) {
        onSelectFollower.onSelectFollower(user);
    }
}
