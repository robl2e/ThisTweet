package com.robl2e.thistweet.ui.tweetlist;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.robl2e.thistweet.R;
import com.robl2e.thistweet.application.TwitterApplication;
import com.robl2e.thistweet.data.local.timeline.TimelineRepository;
import com.robl2e.thistweet.data.local.timeline.TimelineType;
import com.robl2e.thistweet.data.model.timeline.Tweet;
import com.robl2e.thistweet.data.remote.AppResponseHandler;
import com.robl2e.thistweet.data.remote.ErrorCodes;
import com.robl2e.thistweet.ui.common.EndlessRecyclerViewScrollListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public abstract class TweetListFragment extends Fragment {
    private static final String TAG = TweetListFragment.class.getSimpleName();
    private static final long REFRESH_DELAY = 500;

    private RecyclerView tweetList;
    private View emptyView;

    protected TimelineRepository timelineRepository;
    private TweetListAdapter adapter;
    private EndlessRecyclerViewScrollListener endlessScrollListener;
    private Handler handler;

    public abstract TimelineType getTimelineType();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        timelineRepository = new TimelineRepository(TwitterApplication.getRestClient(), getResources());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tweet_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews(view);
        initializeList();
    }

    private void setupViews(View view) {
        emptyView = view.findViewById(R.id.layout_empty_view);
        tweetList = (RecyclerView) view.findViewById(R.id.list_tweets);
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

    private void initializeList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        tweetList.setLayoutManager(layoutManager);
        tweetList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        if (adapter == null) {
            adapter = new TweetListAdapter(getContext(), new ArrayList<TweetViewModel>());
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
                        timelineRepository.getTimeline(getTimelineType()
                                ,viewModel.getId(), new TimeLineResponseHandler(getActivity()) {
                            @Override
                            public void onComplete(Response response, final List<Tweet> tweets) {
                                super.onComplete(response, tweets);
                                if (response.code() == ErrorCodes.API_TOO_MANY_REQUESTS) return;

                                List<TweetViewModel> tweetViewModels = TweetViewModel.convert(tweets);
                                adapter.addItems(tweetViewModels);
                                getActivity().runOnUiThread(new Runnable() {
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
    public void onStart() {
        super.onStart();
        endlessScrollListener.resetState();
        timelineRepository.getTimeline(getTimelineType(),new TimeLineResponseHandler(getActivity()) {
            @Override
            public void onComplete(Response response, List<Tweet> tweets) {
                super.onComplete(response, tweets);
                if (response.code() == ErrorCodes.API_TOO_MANY_REQUESTS) return;
                Log.d(TAG, "Fetched timeline");

                List<TweetViewModel> tweetViewModels = TweetViewModel.convert(tweets);
                adapter.setItems(tweetViewModels);
                getActivity().runOnUiThread(new Runnable() {
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

    protected void addSingleItemToFront(TweetViewModel viewModel) {
        adapter.addItem(viewModel);
        adapter.notifyItemInserted(0);
        tweetList.scrollToPosition(0);
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
