package com.job.github;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.job.github.models.UserModel;

public class MainActivity extends AppCompatActivity implements WebViewFragment.OnGetToken, HomeFragment.OnUserGet {
    private static final String TAG = "MainActivity";
    private static final String HOME_FRAGMENT = "HOME_FRAGMENT";
    private static final String REPOS_FRAGMENT = "REPOS_FRAGMENT";
    private static final String WEBVIEW_FRAGMENT = "WEBVIEW_FRAGMENT";
    private static String CLIENT_ID = "CLIENT_ID";
    private static String CLIENT_SECRET = "CLIENT_SECRET";
    private String mToken;
    private UserModel mUser;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setUpFragment(HomeFragment.newInstance(mToken), HOME_FRAGMENT);
                    return true;
                case R.id.navigation_dashboard:
                    setUpFragment(ReposFragment.newInstance(mUser.getLogin()), REPOS_FRAGMENT);
                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpFragment(WebViewFragment.newInstance(getIntent().getStringExtra(CLIENT_ID), getIntent().getStringExtra(CLIENT_SECRET)), WEBVIEW_FRAGMENT);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public static Intent newInstance(Context context, String clientId, String clientSecret) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(CLIENT_ID, clientId);
        intent.putExtra(CLIENT_SECRET, clientSecret);
        return intent;
    }

    public void setUpFragment(Fragment fragment, String tag) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (currentFragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment, tag)
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .attach(fragment)
                    .commit();
        }
    }

    @Override
    public void onGetToken(String token) {
        mToken = token;
        setUpFragment(HomeFragment.newInstance(token), HOME_FRAGMENT);
        if (token.contains("Error")) {
            Log.d(TAG, "onPostExecute: error" + token);
        } else {
            Log.d(TAG, "onPostExecute: complete" + token);
        }
    }

    @Override
    public void onUserGet(UserModel user) {
        this.mUser = user;
    }
}
