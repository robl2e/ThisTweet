
package com.robl2e.thistweet.data.model.timeline;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Url {

    @SerializedName("expanded_url")
    @Expose
    String expandedUrl;
    @SerializedName("url")
    @Expose
    String url;
    @SerializedName("indices")
    @Expose
    List<Integer> indices = null;
    @SerializedName("display_url")
    @Expose
    String displayUrl;

    public String getExpandedUrl() {
        return expandedUrl;
    }

    public void setExpandedUrl(String expandedUrl) {
        this.expandedUrl = expandedUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
    }

    public String getDisplayUrl() {
        return displayUrl;
    }

    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

}
