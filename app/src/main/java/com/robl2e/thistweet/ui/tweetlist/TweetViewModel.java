package com.robl2e.thistweet.ui.tweetlist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.robl2e.thistweet.data.model.timeline.Entities;
import com.robl2e.thistweet.data.model.timeline.Media;
import com.robl2e.thistweet.data.model.timeline.Tweet;
import com.robl2e.thistweet.data.model.user.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;

/**
 * Created by robl2e on 9/26/17.
 */

public class TweetViewModel {
    private static final String TAG = TweetViewModel.class.getSimpleName();

    private Long id;
    private String text;
    private Integer retweetCount;
    private Integer favoriteCount;
    private String createdAt;
    private Entities entities;
    private User user;

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Integer getRetweetCount() {
        return retweetCount;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Entities getEntities() {
        return entities;
    }

    public User getUser() {
        return user;
    }

    public TweetViewModel(Long id, String text, Integer retweetCount, Integer favoriteCount, String createdAt, Entities entities, User user) {
        this.id = id;
        this.text = text;
        this.retweetCount = retweetCount;
        this.favoriteCount = favoriteCount;
        this.createdAt = createdAt;
        this.entities = entities;
        this.user = user;
    }

    @Nullable
    public String getUsername() {
        if (user == null) return null;

        return user.getName();
    }

    @Nullable
    public String getScreenName() {
        if (user == null) return null;

        return user.getScreenName();
    }

    @Nullable
    public String getProfileImageUrl() {
        if (user == null) return null;
        return user.getProfileImageUrl();
    }

    @Nullable
    public String getTweetImageUrl() {
        if (entities == null) return null;
        if (entities.getMedia() == null) return null;
        if (entities.getMedia().isEmpty()) return null;

        Media media = entities.getMedia().get(0);
        return media.getMediaUrlHttps();
    }

    public DateTime calculateCreatedAtDateTime() {
        String createdAt = getCreatedAt();

        Date date = parseDateString(createdAt);
        if (date == null) return null;

        DateTime createdDateTime = DateTime.forInstant(date.getTime(), getTimeZone());
        return createdDateTime;
    }

    private TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }

    private Date parseDateString(String rawDateTime) {
        if (TextUtils.isEmpty(rawDateTime)) return null;

        // Example "Sat Sep 30 02:24:30 +0000 2017"
        String pattern = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        simpleDateFormat.setLenient(true);
        try {
            return simpleDateFormat.parse(rawDateTime);
        } catch (ParseException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        return null;
    }

    public static TweetViewModel convert(Tweet tweet) {
        return new TweetViewModel(
                tweet.getId(),
                tweet.getText(),
                tweet.getRetweetCount(),
                tweet.getFavoriteCount(),
                tweet.getCreatedAt(),
                tweet.getEntities(),
                tweet.getUser()
        );
    }

    @NonNull
    public static List<TweetViewModel> convert(List<Tweet> tweetList) {
        List<TweetViewModel> tweetViewModels = new ArrayList<>();
        for (Tweet tweet : tweetList) {
            tweetViewModels.add(TweetViewModel.convert(tweet));
        }
        return tweetViewModels;
    }
}
