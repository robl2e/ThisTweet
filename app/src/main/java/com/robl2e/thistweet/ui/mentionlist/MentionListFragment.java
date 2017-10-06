package com.robl2e.thistweet.ui.mentionlist;

import android.os.Bundle;
import android.view.View;

import com.robl2e.thistweet.data.local.timeline.TimelineParams;
import com.robl2e.thistweet.data.local.timeline.TimelineType;
import com.robl2e.thistweet.ui.home.TabPage;
import com.robl2e.thistweet.ui.tweetlist.TweetListFragment;

public class MentionListFragment extends TweetListFragment implements TabPage {
    public MentionListFragment() {
        // Required empty public constructor
    }

    public static MentionListFragment newInstance() {
        MentionListFragment fragment = new MentionListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public TimelineParams getTimelineParams() {
        return new TimelineParams(TimelineType.MENTIONS_TIMELINE);
    }

    @Override
    public void onFABClicked(View v) {

    }
}
