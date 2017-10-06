package com.robl2e.thistweet.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.robl2e.thistweet.R;
import com.robl2e.thistweet.application.TwitterApplication;
import com.robl2e.thistweet.data.local.user.UserRepository;
import com.robl2e.thistweet.data.model.user.User;
import com.robl2e.thistweet.data.remote.AppResponseHandler;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Response;

public class UserProfileActivity extends AppCompatActivity {
    private static final String EXTRA_USER_ID = "EXTRA_USER_ID";
    private UserRepository userRepository;
    private ImageView thumbnailImage;
    private TextView nameText;
    private TextView screenNameText;
    private TextView taglineText;
    private TextView followingText;
    private TextView followersText;
    private Toolbar toolbar;

    public static void start(Activity activity, String userId) {
        Intent intent = new Intent(activity, UserProfileActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userRepository = new UserRepository(TwitterApplication.getRestClient());
        setupViews();
        setupToolbar();
        showUserTimeline();
    }

    @Nullable
    private String extractExtra() {
        if (getIntent() == null) return null;

        if (!getIntent().hasExtra(EXTRA_USER_ID)) return null;

        return getIntent().getStringExtra(EXTRA_USER_ID);
    }

    private void setupViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        thumbnailImage = (ImageView) findViewById(R.id.image_user_thumbnail);
        nameText = (TextView) findViewById(R.id.text_name);
        screenNameText = (TextView) findViewById(R.id.text_screename);
        taglineText = (TextView) findViewById(R.id.text_tagline);
        followingText = (TextView) findViewById(R.id.text_following_count);
        followersText = (TextView) findViewById(R.id.text_followers_count);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String userId = extractExtra();
        userRepository.getUser(userId, new AppResponseHandler<User>() {
            @Override
            public void onFailure(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UserProfileActivity.this
                                , R.string.error_fetch_user_profile
                                , Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onComplete(Response response, final User user) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UserViewModel viewModel = UserViewModel.convert(user);
                        bindUser(viewModel);
                    }
                });
            }
        });
    }

    private void showUserTimeline() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.layout_content);

        if (fragment == null) {
            String userId = extractExtra();
            fragment = UserTweetListFragment.newInstance(userId);
        }

        if (fragment.isAdded()) {
            fm.beginTransaction()
                    .show(fragment)
                    .commit();
        } else {
            fm.beginTransaction()
                    .add(R.id.layout_content, fragment)
                    .commit();
        }
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null) return;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void bindUser(UserViewModel viewModel) {
        if (viewModel == null) return;

        nameText.setText(viewModel.getName());

        String screenNameStr = getString(R.string.screen_name
                , viewModel.getScreenname());
        screenNameText.setText(screenNameStr);
        renderProfileImage(viewModel);
        taglineText.setText(viewModel.getUserTagline());

        renderFollowersCount(viewModel.getFollowersCount());
        renderFollowingCount(viewModel.getFollowingCount());

    }

    private void renderFollowingCount(Integer followingCount) {
        String count = String.valueOf(followingCount);
        String followingCountStr = getString(R.string.following_count, count);

        Spannable word = new SpannableString(followingCountStr);
        word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, count.length()
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        word.setSpan(new StyleSpan(Typeface.BOLD), 0,
                count.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        followingText.setText(word);
    }

    private void renderFollowersCount(Integer followersCount) {
        String count = String.valueOf(followersCount);
        String followersCountStr = getString(R.string.followers_count, count);

        Spannable word = new SpannableString(followersCountStr);
        word.setSpan(new ForegroundColorSpan(Color.BLACK), 0, count.length()
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        word.setSpan(new StyleSpan(Typeface.BOLD), 0,
                count.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        followersText.setText(word);
    }


    private void renderProfileImage(UserViewModel viewModel) {
        String imageUrl = viewModel.getProfileImageUrl();
        if (TextUtils.isEmpty(imageUrl)) return;

        Glide.with(this)
                .load(imageUrl)
                .bitmapTransform(
                        new RoundedCornersTransformation(
                                this
                                ,23,0
                        ))
                .into(thumbnailImage);
    }

}
