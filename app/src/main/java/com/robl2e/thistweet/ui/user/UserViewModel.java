package com.robl2e.thistweet.ui.user;

import com.robl2e.thistweet.data.model.user.User;

/**
 * Created by n7 on 10/5/17.
 */

public class UserViewModel {
    private String name;
    private String screenname;
    private String profileImageUrl;
    private String userTagline;
    private Integer followingCount;
    private Integer followersCount;

    public String getName() {
        return name;
    }

    public String getScreenname() {
        return screenname;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getUserTagline() {
        return userTagline;
    }

    public Integer getFollowingCount() {
        return followingCount;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    private UserViewModel(String name, String screenname, String profileImageUrl, String userTagline, Integer followingCount, Integer followersCount) {
        this.name = name;
        this.screenname = screenname;
        this.profileImageUrl = profileImageUrl;
        this.userTagline = userTagline;
        this.followingCount = followingCount;
        this.followersCount = followersCount;
    }

    public static UserViewModel convert(User user) {
        return new UserViewModel(
            user.getName(),
                user.getScreenName(),
                user.getProfileImageUrl(),
                user.getDescription(),
                user.getFriendsCount(),
                user.getFollowersCount()
        );
    }
}
