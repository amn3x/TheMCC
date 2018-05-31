package com.learn.niceboy.themcc.Application;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

/**
 * Created by Aadarsh on 6/11/2017.
 */

public class MccEventApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //Parse.initialize(this, "xThf8j55lQQWPjx5DVQz50WQfJcJ6RmcINOd8Rrs", "vSr2l0ivxEZ7R7j45NlJiA8MyoCi2buWVILwMs7C");
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("BY5KtQuZKtrWjgXSojGzJrhDtEVVUehkFHy95gB0")
                .clientKey("Ml4tzDayPqOpTOnxWIww9Q9Gmwh8hN9qN14PfkuW")
                .server("https://parseapi.back4app.com/").enableLocalDataStore().build());

        // Update Installation
        /*ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("GCMSenderId", "1094652271608");
        installation.saveInBackground();*/

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.saveInBackground();

        //ParseInstallation.getCurrentInstallation().saveInBackground();

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
}
