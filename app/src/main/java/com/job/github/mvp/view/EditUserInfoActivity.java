package com.job.github.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.job.github.api.pojo.User;

public class EditUserInfoActivity extends SingleFragmentActivity implements EditUserInfoFragment.OnUpdateUserInfo {
    private static final String USER = "USER";
    private static final String TOKEN = "TOKEN";

    @Override
    protected Fragment createFragment() {
        return new EditUserInfoFragment().newInstance(getIntent().getParcelableExtra(USER), getIntent().getStringExtra(TOKEN));
    }

    public static Intent newInstance(Context context, User user, String mToken) {
        Intent intent = new Intent(context, EditUserInfoActivity.class);
        intent.putExtra(USER, user);
        intent.putExtra(TOKEN, mToken);
        return intent;
    }

    @Override
    public void onUpdateUserInfo(User user) {
        Intent intent = new Intent();
        intent.putExtra(USER, user);
        setResult(RESULT_OK, intent);
        finish();
    }
}
