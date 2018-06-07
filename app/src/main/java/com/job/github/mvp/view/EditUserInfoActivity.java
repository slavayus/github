package com.job.github.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.job.github.api.pojo.User;

public class EditUserInfoActivity extends SingleFragmentActivity {
    private static final String USER = "USER";

    @Override
    protected Fragment createFragment() {
        return new EditUserInfoFragment().newInstance(getIntent().getParcelableExtra(USER));
    }

    public static Intent newInstance(Context context, User user) {
        Intent intent = new Intent(context, EditUserInfoActivity.class);
        intent.putExtra(USER, user);
        return intent;
    }
}
