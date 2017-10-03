package com.robl2e.thistweet.ui.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.robl2e.thistweet.R;
import com.robl2e.thistweet.ui.mentionlist.MentionListFragment;
import com.robl2e.thistweet.ui.tweetlist.TweetListFragment;

/**
 * Created by robl2e on 10/2/17.
 */

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 2;

    private static final int PAGE_HOME = 0;
    private static final int PAGE_MENTION = 1;

    private final Context context;

    public HomeFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
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

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case PAGE_HOME:
                return context.getString(R.string.home);
            case PAGE_MENTION:
                return context.getString(R.string.mention);
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
