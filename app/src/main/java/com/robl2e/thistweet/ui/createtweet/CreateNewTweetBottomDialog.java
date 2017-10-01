package com.robl2e.thistweet.ui.createtweet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.robl2e.thistweet.R;
import com.robl2e.thistweet.data.local.timeline.TimelineRepository;
import com.robl2e.thistweet.data.model.timeline.Tweet;
import com.robl2e.thistweet.data.remote.AppResponseHandler;
import com.robl2e.thistweet.ui.util.KeyboardUtil;

import okhttp3.Response;

/**
 * Created by robl2e on 8/15/2017.
 */

public class CreateNewTweetBottomDialog extends BottomDialog {
    private static final String TAG = CreateNewTweetBottomDialog.class.getSimpleName();

    private TimelineRepository timelineRepository;

    private final View customView;
    private final View layoutTextInput;
    private final EditText tweetEditText;
    private final ProgressBar progressBar;
    private Button positiveButton;
    private Button negativeButton;

    private final Listener listener;

    public interface Listener {
        void onCancel();
        void onPostTweet(Tweet tweet);
    }

    public static CreateNewTweetBottomDialog newInstance(Context context
            , TimelineRepository timelineRepository, Listener listener) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View customView = inflater.inflate(R.layout.dialog_create_new_tweet, null);

        Builder builder = new Builder(context)
                .setTitle(R.string.new_tweet)
                .setPositiveText(R.string.tweet)
                .setNegativeText(R.string.cancel)
                .autoDismiss(false)
                .setCancelable(false)
                .setCustomView(customView);

        return new CreateNewTweetBottomDialog(builder, timelineRepository, customView, listener);
    }

    protected CreateNewTweetBottomDialog(Builder builder, TimelineRepository timelineRepository, final View customView, final Listener listener) {
        super(builder);
        this.timelineRepository = timelineRepository;
        this.customView = customView;
        this.listener = listener;

        layoutTextInput = customView.findViewById(R.id.layout_text_input);
        tweetEditText = (EditText) customView.findViewById(R.id.edit_tweet);
        tweetEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (positiveButton != null) {
                    positiveButton.setEnabled(count > 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        progressBar = (ProgressBar) customView.findViewById(R.id.progress_indicator);
        View rootDialogView = customView.getRootView();
        negativeButton = (Button) rootDialogView .findViewById(R.id.bottomDialog_cancel);
        positiveButton = (Button) rootDialogView .findViewById(R.id.bottomDialog_ok);

        builder.onPositive(new ButtonCallback() {
            @Override
            public void onClick(@NonNull BottomDialog bottomDialog) {
                postNewTweet();
            }
        });
        builder.onNegative(new ButtonCallback() {
            @Override
            public void onClick(@NonNull BottomDialog bottomDialog) {
                dismiss();
                if (listener != null) listener.onCancel();
            }
        });
    }

    private void postNewTweet() {
        String tweetText = tweetEditText.getText().toString();
        if (TextUtils.isEmpty(tweetText)) return;

        showProgressIndicator(true);
        timelineRepository.createTweet(tweetText, new AppResponseHandler<Tweet>() {
            @Override
            public void onFailure(Exception e) {
                customView.post(new Runnable() {
                    @Override
                    public void run() {
                        showProgressIndicator(false);
                        showErrorToastMessage();
                    }
                });
            }

            @Override
            public void onComplete(Response response, final Tweet tweet) {
                customView.post(new Runnable() {
                    @Override
                    public void run() {
                        showProgressIndicator(false);
                        notifyListener(tweet);
                        dismiss();
                    }
                });
            }
        });
    }

    @Override
    public void show() {
        super.show();
        tweetEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyboardUtil.showSoftInput(tweetEditText);
            }
        }, tweetEditText.getResources()
                .getInteger(android.R.integer.config_shortAnimTime));
    }

    private void showErrorToastMessage() {
        Toast.makeText(customView.getContext()
                , R.string.error_create_tweet, Toast.LENGTH_SHORT).show();
    }

    private void showProgressIndicator(boolean show) {
        if (show) {
            layoutTextInput.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.VISIBLE);
            if (positiveButton != null) positiveButton.setVisibility(View.INVISIBLE);
            if (negativeButton!= null) negativeButton.setVisibility(View.INVISIBLE);
        } else {
            layoutTextInput.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            if (positiveButton != null) positiveButton.setVisibility(View.VISIBLE);
            if (negativeButton!= null) negativeButton.setVisibility(View.VISIBLE);
        }
    }

    private void notifyListener(Tweet tweet) {
        if (listener != null) {
            if (tweet != null) {
                listener.onPostTweet(tweet);
            }
        }
    }
}