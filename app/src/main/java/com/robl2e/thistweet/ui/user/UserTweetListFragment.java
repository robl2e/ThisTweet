package com.robl2e.thistweet.ui.user;

import android.os.Bundle;

import com.robl2e.thistweet.data.local.timeline.TimelineParams;
import com.robl2e.thistweet.data.local.timeline.TimelineType;
import com.robl2e.thistweet.ui.tweetlist.TweetListFragment;

/**
 * Created by n7 on 10/5/17.
 */

public class UserTweetListFragment extends TweetListFragment {
    private static final String ARG_USER_ID = "ARG_USER_ID";
    private String userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = extractExtra();
    }

    private String extractExtra() {
        if (getArguments() == null) return null;
        if (!getArguments().containsKey(ARG_USER_ID)) return null;

        return getArguments().getString(ARG_USER_ID);
    }

    @Override
    public TimelineParams getTimelineParams() {
        return new TimelineParams(TimelineType.USER_TIMELINE, userId);
    }

    public static UserTweetListFragment newInstance(String userId) {
        UserTweetListFragment fragment = new UserTweetListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }
}
