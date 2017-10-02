package com.robl2e.thistweet.ui.tweetlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.robl2e.thistweet.R;
import com.robl2e.thistweet.application.TwitterApplication;
import com.robl2e.thistweet.data.local.timeline.TimelineRepository;
import com.robl2e.thistweet.data.model.timeline.Tweet;
import com.robl2e.thistweet.data.remote.AppResponseHandler;
import com.robl2e.thistweet.data.remote.ErrorCodes;
import com.robl2e.thistweet.ui.common.EndlessRecyclerViewScrollListener;
import com.robl2e.thistweet.ui.createtweet.CreateNewTweetBottomDialog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class TweetListActivity extends AppCompatActivity {
    private static final String TAG = TweetListActivity.class.getSimpleName();
    private static final long REFRESH_DELAY = 500;

    private Toolbar toolbar;
    private RecyclerView tweetList;
    private View emptyView;
    private FloatingActionButton newTweetFAB;
    private CreateNewTweetBottomDialog newTweetBottomDialog;

    private TimelineRepository timelineRepository;
    private TweetListAdapter adapter;
    private EndlessRecyclerViewScrollListener endlessScrollListener;
    private Handler handler;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, TweetListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_list);
        handler = new Handler();
        timelineRepository = new TimelineRepository(TwitterApplication.getRestClient(), getResources());
        setupViews();
        initializeList();
        initializeFAB();
    }

    private void setupViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.home);

        emptyView = findViewById(R.id.layout_empty_view);
        tweetList = (RecyclerView) findViewById(R.id.list_tweets);
        newTweetFAB = (FloatingActionButton) findViewById(R.id.fab_new_tweet);
    }

    private void showEmptyView(boolean show) {
        if (show) {
            emptyView.setVisibility(View.VISIBLE);
            tweetList.setVisibility(View.INVISIBLE);
        } else {
            emptyView.setVisibility(View.INVISIBLE);
            tweetList.setVisibility(View.VISIBLE);
        }
    }

    private void initializeFAB() {
        newTweetFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewTweetDialog();
            }
        });
    }

    private void showNewTweetDialog() {
        if (newTweetBottomDialog == null) {
            newTweetBottomDialog = CreateNewTweetBottomDialog.newInstance(this
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
                    adapter.addItem(viewModel);
                    adapter.notifyItemInserted(0);
                    tweetList.scrollToPosition(0);
                }
            });
            newTweetBottomDialog.show();
        }
    }

    private void initializeList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        tweetList.setLayoutManager(layoutManager);
        tweetList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        if (adapter == null) {
            adapter = new TweetListAdapter(this, new ArrayList<TweetViewModel>());
        }
        tweetList.setAdapter(adapter);

        endlessScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(final int page, final int totalItemsCount, RecyclerView view) {
                int lastItemIndex = totalItemsCount > 0 ? totalItemsCount - 1 : 0;
                final TweetViewModel viewModel = adapter.getItem(lastItemIndex);
                if (viewModel == null) return;

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        timelineRepository.getTimeline(viewModel.getId(), new TimeLineResponseHandler(TweetListActivity.this) {
                            @Override
                            public void onComplete(Response response, final List<Tweet> tweets) {
                                super.onComplete(response, tweets);
                                if (response.code() == ErrorCodes.API_TOO_MANY_REQUESTS) return;

                                List<TweetViewModel> tweetViewModels = new ArrayList<>();
                                for (Tweet tweet : tweets) {
                                    tweetViewModels.add(TweetViewModel.convert(tweet));
                                }
                                adapter.addItems(tweetViewModels);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showEmptyView(adapter.getItemCount() <= 0);
                                        updateListAdapterWithNewItems(tweets.size());
                                    }
                                });
                            }
                        });
                    }
                }, REFRESH_DELAY);
            }
        };
        tweetList.addOnScrollListener(endlessScrollListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        endlessScrollListener.resetState();
        timelineRepository.getTimeline(new TimeLineResponseHandler(this) {
            @Override
            public void onComplete(Response response, List<Tweet> tweets) {
                super.onComplete(response, tweets);
                if (response.code() == ErrorCodes.API_TOO_MANY_REQUESTS) return;
                Log.d(TAG, "Fetched timeline");

                List<TweetViewModel> tweetViewModels = new ArrayList<>();
                for (Tweet tweet : tweets) {
                    tweetViewModels.add(TweetViewModel.convert(tweet));
                }
                adapter.setItems(tweetViewModels);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showEmptyView(adapter.getItemCount() <= 0);
                        updateListAdapter();
                    }
                });

            }
        });
    }

    private void updateListAdapter() {
        adapter.notifyDataSetChanged();
    }

    private void updateListAdapterWithNewItems(int itemsToInsert) {
        int currentItemCount = adapter.getItemCount();
        adapter.notifyItemRangeInserted(currentItemCount, itemsToInsert);
    }

    private static class TimeLineResponseHandler implements AppResponseHandler<List<Tweet>> {
        private WeakReference<Activity> activityWeakReference;

        TimeLineResponseHandler(Activity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void onFailure(Exception e) {
            final Activity activity = activityWeakReference.get();
            if (activity == null) return;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(activity, R.string.error_fetch_timeline
                            , Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onComplete(Response response, List<Tweet> tweets) {
            int errorCode = response.code();
            if (errorCode == ErrorCodes.API_TOO_MANY_REQUESTS) {
                final Activity activity = activityWeakReference.get();
                if (activity == null) return;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, R.string.error_too_many_requests
                                , Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}
