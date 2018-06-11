package com.job.github.utils;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by slavik on 6/11/18.
 */

public class ClientPreferences {
    private static final ClientPreferences ourInstance = new ClientPreferences();
    private static final String CLIENT_LOGIN = "CLIENT_LOGIN";
    private static final String LAST_REPO = "LAST_REPO";
    private Context context;

    String getUserName() {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(CLIENT_LOGIN, null);
    }

    public static ClientPreferences getInstance(Context context) {
        ourInstance.setContext(context);
        return ourInstance;
    }

    private ClientPreferences() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    String getLastRepo() {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(LAST_REPO, null);
    }

    public void setUserLogin(String userLogin) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(CLIENT_LOGIN, userLogin)
                .apply();
    }

    public void setLastRepo(String lastRepo) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(LAST_REPO, lastRepo)
                .apply();
    }
}
