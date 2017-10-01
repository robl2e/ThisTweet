package com.robl2e.thistweet.ui.tweetlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.robl2e.thistweet.R;
import java.util.List;
import java.util.TimeZone;
import hirondelle.date4j.DateTime;

import static android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
import static android.text.format.DateUtils.FORMAT_ABBREV_RELATIVE;
import static android.text.format.DateUtils.FORMAT_SHOW_DATE;
import static android.text.format.DateUtils.FORMAT_SHOW_YEAR;

/**
 * Created by robl2e on 9/26/17.
 */

public class TweetListAdapter extends RecyclerView.Adapter<TweetListAdapter.ViewHolder> {
    private List<TweetViewModel> items;
    private LayoutInflater inflater;

    public TweetListAdapter(Context context, List<TweetViewModel> items) {
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TweetViewModel viewModel = items.get(position);
        holder.bindItem(viewModel);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.cleanUp();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<TweetViewModel> items) {
        this.items = items;
    }

    public void addItems(List<TweetViewModel> items) {
        this.items.addAll(items);
    }

    public void addItem(TweetViewModel item) {
        this.items.add(0, item);
    }

    public TweetViewModel getItem(int position) {
        return items.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView userProfileImage;
        private TextView usernameText;
        private TextView screenNameText;
        private TextView createAtText;
        private TextView tweetText;
        private ImageView tweetImage;

        private TextView reTweetCountText;
        private TextView favoritesCountText;

        public ViewHolder(View itemView) {
            super(itemView);
            userProfileImage = (ImageView) itemView.findViewById(R.id.image_user_profile);
            usernameText = (TextView) itemView.findViewById(R.id.text_username);
            screenNameText = (TextView) itemView.findViewById(R.id.text_screename);
            createAtText = (TextView) itemView.findViewById(R.id.text_created_at);
            tweetText = (TextView) itemView.findViewById(R.id.text_tweet);
            tweetImage = (ImageView) itemView.findViewById(R.id.image_tweet);

            reTweetCountText = (TextView) itemView.findViewById(R.id.text_retweet_count);
            favoritesCountText = (TextView) itemView.findViewById(R.id.text_favorite_count);
        }

        public void cleanUp() {
            Glide.clear(userProfileImage);
            Glide.clear(tweetImage);
        }

        public void bindItem(TweetViewModel viewModel) {
            if (viewModel == null) return;

            renderProfileImage(viewModel);
            renderProfileNameText(viewModel);
            renderCreatedAtText(viewModel);

            tweetText.setText(viewModel.getText());
            renderTweetImage(viewModel);
            renderTweetCountsText(viewModel);
        }

        private void renderTweetCountsText(TweetViewModel viewModel) {
            Integer retweetCount = viewModel.getRetweetCount();
            if (retweetCount != null) {
                reTweetCountText.setText(String.valueOf(retweetCount));
            }

            Integer favoriteCount = viewModel.getFavoriteCount();
            if (favoriteCount!= null) {
                favoritesCountText.setText(String.valueOf(favoriteCount));
            }
        }

        private void renderCreatedAtText(TweetViewModel viewModel) {
            DateTime createdDateTime = viewModel.calculateCreatedAtDateTime();
            if (createdDateTime == null) return;

            int flags = FORMAT_SHOW_DATE | FORMAT_SHOW_YEAR | FORMAT_ABBREV_MONTH | FORMAT_ABBREV_RELATIVE;
            String relativeDate = DateUtils.getRelativeTimeSpanString(createdDateTime.getMilliseconds(TimeZone.getDefault()),
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS, flags).toString();

            createAtText.setText(relativeDate);
        }

        private void renderProfileImage(TweetViewModel viewModel) {
            String imageUrl = viewModel.getProfileImageUrl();
            if (TextUtils.isEmpty(imageUrl)) return;

            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .into(userProfileImage);
        }

        private void renderTweetImage(TweetViewModel viewModel) {
            String imageUrl = viewModel.getTweetImageUrl();
            if (TextUtils.isEmpty(imageUrl)) {
                tweetImage.setVisibility(View.GONE);
                return;
            }

            tweetImage.setVisibility(View.VISIBLE);
            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .centerCrop()
                    .into(tweetImage);
        }

        private void renderProfileNameText(TweetViewModel viewModel) {
            if (!TextUtils.isEmpty(viewModel.getUsername())) {
                usernameText.setText(viewModel.getUsername());
            }

            if (!TextUtils.isEmpty(viewModel.getScreenName())) {
                String screenName = itemView.getResources().getString(R.string.screen_name
                        , viewModel.getScreenName());
                screenNameText.setText(screenName);
            }
        }

    }
}
