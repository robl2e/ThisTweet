package com.robl2e.thistweet.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.robl2e.thistweet.R;
import com.robl2e.thistweet.ui.user.UserProfileActivity;


public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout homeTab;
    private ViewPager homeViewPager;
    private FloatingActionButton homeFAB;
    private HomeFragmentPagerAdapter pagerAdapter;

    // Tab icons
    private int[] imageResId = {
            R.drawable.ic_home_outline,
            R.drawable.ic_bell_outline
    };

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
                renderTabIcon(tab, true);
                renderToolbarTitle(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                showFAB(tab);
                renderTabIcon(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        for (int i = 0; i < imageResId.length; i++) {
            TabLayout.Tab tab = homeTab.getTabAt(i);
            boolean isSelected = i == homeTab.getSelectedTabPosition();
            if (tab != null) renderTabIcon(tab, isSelected);
        }

        TabLayout.Tab selectedTab =
                homeTab.getTabAt(homeTab.getSelectedTabPosition());
        renderToolbarTitle(selectedTab);
    }

    private void renderTabIcon(TabLayout.Tab tab, boolean isSelected) {
        Drawable iconDrawable =
                ContextCompat.getDrawable(this, imageResId[tab.getPosition()]);
        iconDrawable = iconDrawable.mutate();

        int color = ContextCompat.getColor(this, android.R.color.black);
        if (isSelected) {
            color = ContextCompat.getColor(this, R.color.colorPrimary);
        }
        DrawableCompat.setTint(iconDrawable, color);
        tab.setIcon(iconDrawable);
    }

    private void renderToolbarTitle(TabLayout.Tab tab) {
        String title = pagerAdapter.getToolbarTitle(tab.getPosition());
        if (TextUtils.isEmpty(title)) return;

        toolbar.setTitle(title);
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        homeTab = (TabLayout) findViewById(R.id.tab_home);
        homeViewPager = (ViewPager) findViewById(R.id.viewpager_home);
        homeFAB = (FloatingActionButton) findViewById(R.id.fab_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_user_account:
                UserProfileActivity.start(this, null);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
