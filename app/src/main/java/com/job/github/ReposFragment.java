package com.job.github;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.job.github.adapter.ReposAdapter;
import com.job.github.component.DaggerReposFragmentComponent;
import com.job.github.component.DaggerReposPresenterComponent;
import com.job.github.pojo.Repos;
import com.job.github.presenter.ReposContractView;
import com.job.github.presenter.ReposPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ReposFragment extends Fragment implements ReposContractView {
    private static final String USER_NAME = "USER_NAME";
    private static final String TAG = "ReposFragment";
    private static String CLIENT_ID = "CLIENT_ID";
    private static String CLIENT_SECRET = "CLIENT_SECRET";
    @BindView(R.id.recycler_view_repos) RecyclerView mRecyclerView;
    private String mUserLogin;
    private String mClientId;
    private String mClientSecret;
    private LoaderFragment loaderFragment;
    private Unbinder bind;
    @Inject ReposPresenter mPresenter;
    @Inject ReposAdapter mReposAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadDataFromArguments();
        Log.d(TAG, "onCreate: created repos fragment");
    }

    private void loadDataFromArguments() {
        Bundle arguments = getArguments();
        if (arguments == null) {
            throw new IllegalArgumentException("There is no user name in the arguments");
        } else {
            mUserLogin = arguments.getString(USER_NAME);
            mClientId = arguments.getString(CLIENT_ID);
            mClientSecret = arguments.getString(CLIENT_SECRET);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repos, container, false);

        bind = ButterKnife.bind(this, view);

        DaggerReposFragmentComponent
                .builder()
                .reposPresenterComponent(DaggerReposPresenterComponent.create())
                .build()
                .injectReposFragment(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mReposAdapter);

        mPresenter.attachView(this);
        mPresenter.viewIsReady();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroyView();
        bind.unbind();
    }

    @Override
    public void showRepos(List<Repos> data) {
        mReposAdapter.setData(data);
    }

    @Override
    public String getUserLogin() {
        return mUserLogin;
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
    public void showLoadReposError() {
        if (getActivity() == null) {
            return;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.user_load_repos_error_title)
                .setMessage(R.string.user_load_repos_error_message)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> getActivity().finishAffinity())
                .setCancelable(false)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void showLoaderFragment() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        loaderFragment = new LoaderFragment();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, loaderFragment)
                .commit();
    }

    @Override
    public void stopLoaderFragment() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            return;
        }
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .remove(loaderFragment)
                .commit();

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
