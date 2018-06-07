package com.job.github.mvp.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private static final String TOKEN = "TOKEN";
    private User mUser;
    private Unbinder mBind;
    @Inject EditUserInfoPresenter mPresenter;
    @BindView(R.id.user_name_edit_text) TextView mUserName;
    @BindView(R.id.user_email_edit_text) TextView mUserEmail;
    @BindView(R.id.user_bio_edit_text) TextView mUserBio;
    @BindView(R.id.user_url_edit_text) TextView mUserUrl;
    @BindView(R.id.user_company_edit_text) TextView mUserCompany;
    @BindView(R.id.user_location_edit_text) TextView mUserLocation;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    private ProgressDialog dialog;
    private String mToken;
    private OnUpdateUserInfo onUpdateUserInfo;

    public interface OnUpdateUserInfo {
        void onUpdateUserInfo(User user);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onUpdateUserInfo = ((OnUpdateUserInfo) context);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement OnUpdateUserInfo");
        }
    }

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

        setHasOptionsMenu(true);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(mToolbar);
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_user_info_menu, menu);
    }

    @Override
    public void showProgressDialog() {
        dialog = new ProgressDialog(this.getContext());
        dialog.setMessage(getResources().getString(R.string.updating_user_data));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public User getUser() {
        User user = new User();
        user.setName(mUserName.getText().toString());
        user.setEmail(mUserEmail.getText().toString());
        user.setBio(mUserBio.getText().toString());
        user.setBlog(mUserUrl.getText().toString());
        user.setCompany(mUserCompany.getText().toString());
        user.setLocation(mUserLocation.getText().toString());
        return user;
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
    public void makeToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void stopFragment(User user) {
        onUpdateUserInfo.onUpdateUserInfo(user);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_done:
                mPresenter.editIsDone();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void readFromArguments() {
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(USER);
            mToken = getArguments().getString(TOKEN);
        }
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

    public EditUserInfoFragment newInstance(User user, String token) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(USER, user);
        bundle.putString(TOKEN, token);

        EditUserInfoFragment editUserInfoFragment = new EditUserInfoFragment();
        editUserInfoFragment.setArguments(bundle);
        return editUserInfoFragment;
    }
}
