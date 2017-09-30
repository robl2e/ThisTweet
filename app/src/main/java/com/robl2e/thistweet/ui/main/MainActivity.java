package com.robl2e.thistweet.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.robl2e.thistweet.application.TwitterApplication;
import com.robl2e.thistweet.ui.login.LoginActivity;
import com.robl2e.thistweet.ui.tweetlist.TweetListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isAuthenticated()) {
            LoginActivity.start(this);
        } else {
            TwitterApplication.getRestClient().initialize();
            TweetListActivity.start(this);
        }
        finish();
    }

    private boolean isAuthenticated() {
        return TwitterApplication.getRestClient().isAuthenticated();
    }
}
