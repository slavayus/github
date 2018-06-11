package com.job.github.mvp.view;

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
import com.job.github.dagger.component.DaggerFollowersFragmentComponent;
import com.job.github.mvp.presenter.FollowersFragmentContractView;
import com.job.github.mvp.presenter.FollowersFragmentPresenter;
import com.job.github.mvp.view.adapter.FollowersAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by slavik on 6/11/18.
 */

public class FollowersFragment extends Fragment implements FollowersFragmentContractView {
    public static final String USER_LOGIN = "USER_LOGIN";
    @Inject FollowersAdapter mAdapter;
    @Inject FollowersFragmentPresenter mPresenter;
    @BindView(R.id.recycler_view_fragment) RecyclerView mRecyclerView;
    private Unbinder bind;
    private String mUserLogin;

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

    public static Fragment newInstance(String userLogin) {
        Bundle bundle = new Bundle();
        bundle.putString(USER_LOGIN, userLogin);

        FollowersFragment followersFragment = new FollowersFragment();
        followersFragment.setArguments(bundle);
        return followersFragment;
    }

    @Override
    public String getUserLogin() {
        return mUserLogin;
    }
}
