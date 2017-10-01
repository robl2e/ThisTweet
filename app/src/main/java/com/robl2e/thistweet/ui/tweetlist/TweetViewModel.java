package com.robl2e.thistweet.ui.tweetlist;

import android.support.annotation.Nullable;

import com.robl2e.thistweet.data.model.timeline.Entities;
import com.robl2e.thistweet.data.model.timeline.Media;
import com.robl2e.thistweet.data.model.timeline.Tweet;
import com.robl2e.thistweet.data.model.timeline.User;

import java.util.TimeZone;

import hirondelle.date4j.DateTime;

/**
 * Created by robl2e on 9/26/17.
 */

public class TweetViewModel {
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

    @Nullable
    public Integer getNumDaysFromCreation() {
        String createdAt = getCreatedAt();

        if (!DateTime.isParseable(createdAt)) return null;

        DateTime dateTime = new DateTime(createdAt);
        DateTime now = DateTime.now(TimeZone.getDefault());

        return now.numDaysFrom(dateTime);
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
}
