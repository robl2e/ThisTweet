package com.robl2e.thistweet.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.robl2e.thistweet.R;


public class HomeActivity extends AppCompatActivity {
    private TabLayout homeTab;
    private ViewPager homeViewPager;
    private FloatingActionButton homeFAB;
    private HomeFragmentPagerAdapter pagerAdapter;

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
        initializeFAB();
    }

    private void initializeTab() {
        pagerAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager(), HomeActivity.this);
        homeViewPager.setAdapter(pagerAdapter);
        homeTab.setupWithViewPager(homeViewPager);
        homeTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                showFAB(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                showFAB(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initializeFAB() {
        homeFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = pagerAdapter.getCurrentFragment();
                if (fragment == null) return;

                TabPage page = (TabPage) fragment;
                page.onFABClicked(v);
            }
        });
    }

    private void showFAB(TabLayout.Tab tab) {
        if (tab.getPosition() == HomeFragmentPagerAdapter.PAGE_HOME) {
            homeFAB.setVisibility(View.VISIBLE);
        } else {
            homeFAB.setVisibility(View.INVISIBLE);
        }
    }

    private void setupViews() {
        homeTab = (TabLayout) findViewById(R.id.tab_home);
        homeViewPager = (ViewPager) findViewById(R.id.viewpager_home);
        homeFAB = (FloatingActionButton) findViewById(R.id.fab_home);
    }
}
