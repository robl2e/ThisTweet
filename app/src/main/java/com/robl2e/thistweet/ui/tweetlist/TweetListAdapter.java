package com.robl2e.thistweet.ui.tweetlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robl2e.thistweet.R;

import java.util.List;

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
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void bindItem(TweetViewModel viewModel) {

        }
    }
}
