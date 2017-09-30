package com.robl2e.thistweet.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codepath.oauth.OAuthLoginActivity;
import com.robl2e.thistweet.R;
import com.robl2e.thistweet.data.remote.TwitterClient;
import com.robl2e.thistweet.ui.tweetlist.TweetListActivity;

public class LoginActivity extends OAuthLoginActivity<TwitterClient> {
    private String TAG = LoginActivity.class.getSimpleName();
    private Button signInButton;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupViews();
    }

    private void setupViews() {
        signInButton = (Button) findViewById(R.id.button_sign_in);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginToRest(v);
            }
        });
    }

    @Override
    public void onLoginSuccess() {
        TweetListActivity.start(this);
        finish();
    }

    // Fires if the authentication process fails for any reason.
    @Override
    public void onLoginFailure(Exception e) {
        Log.e(TAG, Log.getStackTraceString(e));
        Toast.makeText(this, R.string.error_login_unsuccessful, Toast.LENGTH_SHORT).show();
    }

    // Method to be called to begin the authentication process
    // assuming user is not authenticated.
    // Typically used as an event listener for a button for the user to press.
    public void loginToRest(View view) {
        getClient().setRequestIntentFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getClient().connect();
    }

}
