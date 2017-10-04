package com.robl2e.thistweet.data.local.timeline;

/**
 * Created by robl2e on 10/3/17.
 */

public enum TimelineType {
    HOME_TIMELINE("home_timeline"),
    MENTIONS_TIMELINE("mentions_timeline"),
    USER_TIMELINE("user_timeline");

    private String value;

    TimelineType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
