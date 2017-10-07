
package com.robl2e.thistweet.data.model.timeline;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class UserEntities {

    @SerializedName("url")
    @Expose
    Urls url;
    @SerializedName("description")
    @Expose
    Description description;

    public Urls getUrl() {
        return url;
    }

    public void setUrl(Urls url) {
        this.url = url;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

}
