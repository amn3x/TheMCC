package com.learn.niceboy.themcc.Application;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;

/**
 * Created by Aadarsh on 6/9/2017.
 */

public class MobileInternetConnectionDetector {
    private Context _context;

    public MobileInternetConnectionDetector(Context context) {
        this._context = context;
    }

    public boolean checkMobileInternetConn() {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //Create object for ConnectivityManager class which returns network related info
        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        //If connectivity object is not null
        if (connectivity != null) {
            //Get network info - Mobile internet access
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo info_1 = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (info != null || info_1!= null) {
                //Look for whether device is currently connected to Mobile internet
                if (info.isConnected()) {
                    return true;
                }
                else if (info_1.isConnected()) {
                    return  true;
                }
            }
        }
        return false;
    }
}
