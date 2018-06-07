package com.job.github.mvp.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.job.github.R;
import com.job.github.api.pojo.User;
import com.job.github.dagger.component.DaggerEditUserInfoComponent;
import com.job.github.mvp.presenter.EditUserInfoContractView;
import com.job.github.mvp.presenter.EditUserInfoPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by slavik on 6/7/18.
 */

public class EditUserInfoFragment extends Fragment implements EditUserInfoContractView {
    private static final String USER = "USER";
    private User mUser;
    private Unbinder mBind;
    @Inject EditUserInfoPresenter mPresenter;
    @BindView(R.id.user_name_edit_text) TextView mUserName;
    @BindView(R.id.user_email_edit_text) TextView mUserEmail;
    @BindView(R.id.user_bio_edit_text) TextView mUserBio;
    @BindView(R.id.user_url_edit_text) TextView mUserUrl;
    @BindView(R.id.user_company_edit_text) TextView mUserCompany;
    @BindView(R.id.user_location_edit_text) TextView mUserLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_user_info, container, false);
        mBind = ButterKnife.bind(this, view);

        DaggerEditUserInfoComponent
                .create()
                .injectEditUserInfo(this);

        readFromArguments();

        mPresenter.attachView(this);
        mPresenter.viewIsReady();

        return view;
    }

    private void readFromArguments() {
        mUser = getArguments() != null ? getArguments().getParcelable(USER) : new User();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroyView();
        mBind.unbind();
    }

    @Override
    public void initializeFields() {
        mUserName.setText(mUser.getName());
        mUserEmail.setText(mUser.getEmail());
        mUserBio.setText(mUser.getBio());
        mUserUrl.setText(mUser.getBlog());
        mUserCompany.setText(mUser.getCompany());
        mUserLocation.setText(mUser.getLocation());
    }

    public EditUserInfoFragment newInstance(User user) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(USER, user);

        EditUserInfoFragment editUserInfoFragment = new EditUserInfoFragment();
        editUserInfoFragment.setArguments(bundle);
        return editUserInfoFragment;
    }
}
