package com.job.github;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    private TextView mToolbarSubtitle;
    private TextView userBio;
    private TextView userCompany;
    private TextView userLocation;
    private TextView userBlog;
    private TextView userEmail;
    private HomePresenter mPresenter;

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

        HomeContractModel homeModel = new HomeModel();
        mPresenter = new HomePresenter(homeModel);
        mPresenter.attachView(this);


        mToolbar = view.findViewById(R.id.toolbar);
        mToolbarSubtitle = view.findViewById(R.id.action_bar_subtitle);
        mUserAvatar = view.findViewById(R.id.user_avatar);

        userBio = view.findViewById(R.id.user_bio);
        userCompany = view.findViewById(R.id.user_company);
        userLocation = view.findViewById(R.id.user_location);
        userBlog = view.findViewById(R.id.user_blog);
        userEmail = view.findViewById(R.id.user_email);

        userBio.setOnClickListener(v -> mPresenter.userBioButtonClick());
        userEmail.setOnClickListener(v -> mPresenter.userEmailButtonClick());
        userBlog.setOnClickListener(v -> mPresenter.userBlogButtonClick());

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> startActivity(new Intent(HomeFragment.this.getContext(), EditUserInfoActivity.class)));

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
