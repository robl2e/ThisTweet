package com.robl2e.thistweet.data.local.timeline;


/**
 * Created by n7 on 10/5/17.
 */

public class TimelineParams {
    public final TimelineType type;
    public final String id;

    public TimelineParams(TimelineType type, String id) {
        this.type = type;
        this.id = id;
    }

    public TimelineParams(TimelineType type) {
        this.type = type;
        this.id = null;
    }
}
