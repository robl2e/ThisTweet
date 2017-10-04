package com.robl2e.thistweet.ui.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.robl2e.thistweet.R;
import com.robl2e.thistweet.ui.mentionlist.MentionListFragment;
import com.robl2e.thistweet.ui.tweetlist.TweetListFragment;

/**
 * Created by robl2e on 10/2/17.
 */

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 2;

    public static final int PAGE_HOME = 0;
    public static final int PAGE_MENTION = 1;
    private Fragment currentFragment;

    private final Context context;

    public HomeFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case PAGE_HOME:
                return TweetListFragment.newInstance();
            case PAGE_MENTION:
                return MentionListFragment.newInstance();
        }
        return null;
    }

    public String getToolbarTitle(int position) {
        switch (position) {
            case PAGE_HOME:
                return context.getString(R.string.home);
            case PAGE_MENTION:
                return context.getString(R.string.mention);
        }
        return "";
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
