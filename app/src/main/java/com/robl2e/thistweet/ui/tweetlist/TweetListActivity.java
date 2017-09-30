package com.robl2e.thistweet.ui.tweetlist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.robl2e.thistweet.R;

public class TweetListActivity extends AppCompatActivity {

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, TweetListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_list);
    }
}
