package com.robl2e.thistweet.ui.tweetlist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.robl2e.thistweet.R;
import com.robl2e.thistweet.application.TwitterApplication;
import com.robl2e.thistweet.data.local.timeline.TimelineRepository;
import com.robl2e.thistweet.data.model.timeline.Tweet;
import com.robl2e.thistweet.data.remote.AppResponseHandler;

import java.util.List;

import okhttp3.Response;

public class TweetListActivity extends AppCompatActivity {
    private static final String TAG = TweetListActivity.class.getSimpleName();

    private TimelineRepository timelineRepository;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, TweetListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_list);
        timelineRepository = new TimelineRepository(TwitterApplication.getRestClient(), getResources());
    }

    @Override
    protected void onStart() {
        super.onStart();
        timelineRepository.getTimeline(0, new AppResponseHandler<List<Tweet>>() {

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(TweetListActivity.this, R.string.error_fetch_timeline
                        , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(Response response, List<Tweet> tweets) {
                Log.d(TAG, "Fetched timeline");
            }
        });
    }
}
