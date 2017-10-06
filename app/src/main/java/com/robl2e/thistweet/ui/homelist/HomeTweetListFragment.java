package com.robl2e.thistweet.ui.homelist;

import android.os.Bundle;
import android.view.View;

import com.robl2e.thistweet.data.local.timeline.TimelineParams;
import com.robl2e.thistweet.data.local.timeline.TimelineType;
import com.robl2e.thistweet.data.model.timeline.Tweet;
import com.robl2e.thistweet.ui.createtweet.CreateNewTweetBottomDialog;
import com.robl2e.thistweet.ui.home.TabPage;
import com.robl2e.thistweet.ui.tweetlist.TweetListFragment;
import com.robl2e.thistweet.ui.tweetlist.TweetViewModel;

/**
 * Created by robl2e on 10/3/17.
 */

public class HomeTweetListFragment extends TweetListFragment implements TabPage {
    private CreateNewTweetBottomDialog newTweetBottomDialog;

    public static HomeTweetListFragment newInstance() {
        Bundle args = new Bundle();
        HomeTweetListFragment fragment = new HomeTweetListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public TimelineParams getTimelineParams() {
        return new TimelineParams(TimelineType.HOME_TIMELINE);
    }

    @Override
    public void onFABClicked(View v) {
        showNewTweetDialog();
    }

    private void showNewTweetDialog() {
        if (newTweetBottomDialog == null) {
            newTweetBottomDialog = CreateNewTweetBottomDialog.newInstance(getContext()
                    , timelineRepository, new CreateNewTweetBottomDialog.Listener() {
                        @Override
                        public void onCancel() {
                            newTweetBottomDialog = null;
                        }

                        @Override
                        public void onPostTweet(Tweet tweet) {
                            newTweetBottomDialog = null;
                            TweetViewModel viewModel =
                                    TweetViewModel.convert(tweet);
                            addSingleItemToFront(viewModel);
                        }
                    });
            newTweetBottomDialog.show();
        }
    }
}
