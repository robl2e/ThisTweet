
package com.robl2e.thistweet.data.model.timeline;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExtendedEntities {

    @SerializedName("media")
    @Expose
    private List<Media> media = null;

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

}
