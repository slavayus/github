package com.job.github.mvp.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.job.github.R;
import com.job.github.api.pojo.User;
import com.job.github.dagger.component.DaggerHomePresenterComponent;
import com.job.github.dagger.module.ApplicationContextModule;
import com.job.github.mvp.presenter.HomeContractView;
import com.job.github.mvp.presenter.HomePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends Fragment implements HomeContractView {
    private static final String TAG = "HomeFragment";
    private static final String TOKEN = "token";
    private static final int EDIT_USER_INFO = 0;
    private static final String USER = "USER";
    private String mToken;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.user_avatar) ImageView mUserAvatar;
    private OnUserGet mOnUserGetListener;
    private ProgressDialog dialog;
    @BindView(R.id.action_bar_subtitle) TextView mToolbarSubtitle;
    @BindView(R.id.user_bio) TextView userBio;
    @BindView(R.id.user_company) TextView userCompany;
    @BindView(R.id.user_location) TextView userLocation;
    @BindView(R.id.user_blog) TextView userBlog;
    @BindView(R.id.user_email) TextView userEmail;
    @Inject HomePresenter mPresenter;
    private Unbinder bind;
    private User user;

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
        Log.d(TAG, "onCreate: ");
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
        this.user = data;
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
        bind = ButterKnife.bind(this, view);

        DaggerHomePresenterComponent
                .builder()
                .applicationContextModule(new ApplicationContextModule(getContext()))
                .build()
                .injectHomeFragment(this);

        mPresenter.attachView(this);
        mPresenter.viewIsReady();
        Log.d(TAG, "onCreateView: ");
        return view;
    }


    @OnClick(R.id.fab)
    void fabClick() {
        startActivityForResult(EditUserInfoActivity.newInstance(getContext(), user, mToken), EDIT_USER_INFO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EDIT_USER_INFO:
                if (resultCode == Activity.RESULT_OK) {
                    user = data.getParcelableExtra(USER);
                    updateUserInfo(user);
                }
                break;
        }
    }

    @OnClick({R.id.user_bio, R.id.user_email, R.id.user_blog})
    void userInfoClick(View view) {
        switch (view.getId()) {
            case R.id.user_bio:
                mPresenter.userBioButtonClick();
                break;
            case R.id.user_email:
                mPresenter.userEmailButtonClick();
                break;
            case R.id.user_blog:
                mPresenter.userBlogButtonClick();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.destroyView();
        bind.unbind();
        Log.d(TAG, "onDestroyView: ");
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
    public void showErrorUpdateUserInfoDialog() {
        if (getActivity() == null) {
            return;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.user_home_dialog_error_title)
                .setMessage(R.string.user_home_dialog_error_update_user_info_message)
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss())
                .setCancelable(false)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void updateToolbarText(String name, String login) {
        mToolbar.setTitle(name);
        mToolbarSubtitle.setText(login);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(mToolbar);
        }
    }

    @Override
    public void openBrowser() {
        String url = userBlog.getText().toString();
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void openMail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + userEmail.getText()));
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(emailIntent);
    }

    @Override
    public void openEditBioDialog() {
        if (getContext() == null) {
            return;
        }
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_bio_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = dialogView.findViewById(R.id.edit_bio);

        dialogBuilder.setTitle(R.string.user_bio_alert_dialog_title);
        dialogBuilder.setPositiveButton(R.string.done_button, (dialog, whichButton) -> mPresenter.newBio(edt.getText().toString()));
        dialogBuilder.setNegativeButton(R.string.cancel_button, (dialog, whichButton) -> dialog.dismiss());
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    @Override
    public void updateUserInfo(User user) {
        if (user.getBio() != null) {
            userBio.setText(user.getBio());
        }

        if (user.getCompany() == null) {
            userCompany.setVisibility(View.GONE);
        } else {
            userCompany.setVisibility(View.VISIBLE);
            userCompany.setText(user.getCompany());
        }

        if (user.getLogin() == null) {
            userLocation.setVisibility(View.GONE);
        } else {
            userLocation.setVisibility(View.VISIBLE);
            userLocation.setText(user.getLocation());
        }

        if (user.getEmail() == null) {
            userEmail.setVisibility(View.GONE);
        } else {
            userEmail.setVisibility(View.VISIBLE);
            userEmail.setText(user.getEmail());
        }

        if (user.getBlog() == null) {
            userBlog.setVisibility(View.GONE);
        } else {
            userBlog.setVisibility(View.VISIBLE);
            userBlog.setText(user.getBlog());
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
