package com.robl2e.thistweet.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.robl2e.thistweet.R;
import com.robl2e.thistweet.application.TwitterApplication;
import com.robl2e.thistweet.data.local.user.UserRepository;
import com.robl2e.thistweet.data.model.user.User;
import com.robl2e.thistweet.data.remote.AppResponseHandler;

import okhttp3.Response;

public class UserProfileActivity extends AppCompatActivity {

    private UserRepository userRepository;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, UserProfileActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userRepository = new UserRepository(TwitterApplication.getRestClient());
    }

    @Override
    protected void onStart() {
        super.onStart();
        userRepository.getUser(new AppResponseHandler<User>() {
            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onComplete(Response response, User user) {

            }
        });
    }
}
