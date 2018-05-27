package com.job.github;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private static String CLIENT_ID = "CLIENT_ID";
    private static String CLIENT_SECRET = "CLIENT_SECRET";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

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

        setUpFragment(WebViewFragment.newInstance(getIntent().getStringExtra(CLIENT_ID), getIntent().getStringExtra(CLIENT_SECRET)));

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public static Intent newInstance(Context context, String clientId, String clientSecret) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(CLIENT_ID, clientId);
        intent.putExtra(CLIENT_SECRET, clientSecret);
        return intent;
    }

    public void setUpFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }
}
