package com.robl2e.thistweet.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.robl2e.thistweet.R;

public class HomeActivity extends AppCompatActivity {
    private TabLayout homeTab;
    private ViewPager homeViewPager;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupViews();
        initializeTab();
    }

    private void initializeTab() {
        homeViewPager.setAdapter(new HomeFragmentPagerAdapter(getSupportFragmentManager(),
                HomeActivity.this));
        homeTab.setupWithViewPager(homeViewPager);
    }

    private void setupViews() {
        homeTab = (TabLayout) findViewById(R.id.tab_home);
        homeViewPager = (ViewPager) findViewById(R.id.viewpager_home);
    }
}
