package com.robl2e.thistweet.application;

import android.app.Application;
import android.content.Context;

import com.robl2e.thistweet.data.remote.TwitterClient;

/**
 * Created by robl2e on 9/27/17.
 */

public class TwitterApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = TwitterApplication.this;
    }

    public static TwitterClient getRestClient() {
        return (TwitterClient) TwitterClient.getInstance(TwitterClient.class, context);
    }
}
