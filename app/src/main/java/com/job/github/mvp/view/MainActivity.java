package com.job.github.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.job.github.R;
import com.job.github.api.pojo.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements WebViewFragment.OnGetToken, HomeFragment.OnUserGet {
    private static final String TAG = "MainActivity";
    private static final String HOME_FRAGMENT = "HOME_FRAGMENT";
    private static final String REPOS_FRAGMENT = "REPOS_FRAGMENT";
    private static final String WEBVIEW_FRAGMENT = "WEBVIEW_FRAGMENT";
    private static String CLIENT_ID = "CLIENT_ID";
    private static String CLIENT_SECRET = "CLIENT_SECRET";
    private String mToken;
    private User mUser;
    @BindView(R.id.navigation) BottomNavigationView navigation;
    private String clientId;
    private String clientSecret;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (mToken != null) {
                        setUpHomeFragment();
                        return true;
                    } else {
                        return false;
                    }
                case R.id.navigation_repos:
                    if (mUser != null) {
                        setUpReposFragment();
                        return true;
                    } else {
                        return false;
                    }
                case R.id.navigation_stars:

                    return true;
            }
            return false;
        }
    };

    private void setUpReposFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(REPOS_FRAGMENT);
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, ReposFragment.newInstance(mUser.getLogin(), clientId, clientSecret), REPOS_FRAGMENT)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .attach(fragment)
                    .commit();
        }
    }

    private void setUpHomeFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(HOME_FRAGMENT);
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment.newInstance(mToken), HOME_FRAGMENT)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .attach(fragment)
                    .commit();
        }
    }

    public void setUpWevViewFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(WEBVIEW_FRAGMENT);
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, WebViewFragment.newInstance(clientId, clientSecret), WEBVIEW_FRAGMENT)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .attach(fragment)
                    .commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Log.d(TAG, "onCreate: ");

        clientId = getIntent().getStringExtra(CLIENT_ID);
        clientSecret = getIntent().getStringExtra(CLIENT_SECRET);
        setUpWevViewFragment();

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setVisibility(View.GONE);
    }

    public static Intent newInstance(Context context, String clientId, String clientSecret) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(CLIENT_ID, clientId);
        intent.putExtra(CLIENT_SECRET, clientSecret);
        return intent;
    }

    @Override
    public void onGetToken(String token) {
        mToken = token;
        setUpHomeFragment();
        navigation.setVisibility(View.VISIBLE);
    }

    @Override
    public void onUserGet(User user) {
        this.mUser = user;
    }
}
