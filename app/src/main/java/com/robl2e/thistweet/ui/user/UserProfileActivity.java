package com.robl2e.thistweet.ui.user;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.robl2e.thistweet.R;
import com.robl2e.thistweet.application.TwitterApplication;
import com.robl2e.thistweet.data.local.user.UserRepository;
import com.robl2e.thistweet.data.model.user.User;
import com.robl2e.thistweet.data.remote.AppResponseHandler;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Response;

public class UserProfileActivity extends AppCompatActivity {
    private static final String EXTRA_USER = "EXTRA_USER";
    private UserRepository userRepository;
    private ImageView thumbnailImage;
    private TextView nameText;
    private TextView screenNameText;
    private TextView taglineText;
    private TextView followingText;
    private TextView followersText;
    private Toolbar toolbar;

    public static void start(Activity activity, User user) {
        Intent intent = new Intent(activity, UserProfileActivity.class);
        intent.putExtra(EXTRA_USER, Parcels.wrap(user));
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        userRepository = new UserRepository(TwitterApplication.getRestClient());
        setupViews();
        setStatusBarView();
        setupToolbar();
        showUserTimeline();

        User user = extractExtra();
        if (user != null) {
            bindUser(UserViewModel.convert(user));
        }
    }

    private void setStatusBarView() {
        Window window = getWindow();
        // finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        window.getDecorView().setSystemUiVisibility(0);
    }

    @Nullable
    private User extractExtra() {
        if (getIntent() == null) return null;

        if (!getIntent().hasExtra(EXTRA_USER)) return null;

        return Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_USER));
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
        User user = extractExtra();
        String userId = user != null ? user.getIdStr() : null;
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
            User user = extractExtra();
            String userId = user != null ? user.getIdStr() : null;
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }
        return super.onOptionsItemSelected(item);
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
