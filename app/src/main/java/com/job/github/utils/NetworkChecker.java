package com.job.github.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by slavik on 6/3/18.
 */

public class NetworkChecker {
    private Context context;

    public NetworkChecker(Context context) {
        this.context = context;
    }

    public boolean checkNetwork() {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return !(conMgr != null && conMgr.getActiveNetworkInfo() == null);
    }
}
