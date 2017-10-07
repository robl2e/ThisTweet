
package com.robl2e.thistweet.data.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.robl2e.thistweet.data.model.timeline.UserEntities;

import org.parceler.Parcel;

@Parcel
public class User {

    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("profile_sidebar_fill_color")
    @Expose
    String profileSidebarFillColor;
    @SerializedName("profile_background_tile")
    @Expose
    Boolean profileBackgroundTile;
    @SerializedName("profile_sidebar_border_color")
    @Expose
    String profileSidebarBorderColor;
    @SerializedName("profile_image_url")
    @Expose
    String profileImageUrl;
    @SerializedName("created_at")
    @Expose
    String createdAt;
    @SerializedName("location")
    @Expose
    String location;
    @SerializedName("follow_request_sent")
    @Expose
    Boolean followRequestSent;
    @SerializedName("id_str")
    @Expose
    String idStr;
    @SerializedName("is_translator")
    @Expose
    Boolean isTranslator;
    @SerializedName("profile_link_color")
    @Expose
    String profileLinkColor;
    @SerializedName("entities")
    @Expose
    UserEntities entities;
    @SerializedName("default_profile")
    @Expose
    Boolean defaultProfile;
    @SerializedName("url")
    @Expose
    String url;
    @SerializedName("contributors_enabled")
    @Expose
    Boolean contributorsEnabled;
    @SerializedName("favourites_count")
    @Expose
    Integer favouritesCount;
    @SerializedName("utc_offset")
    @Expose
    Integer utcOffset;
    @SerializedName("profile_image_url_https")
    @Expose
    String profileImageUrlHttps;
    @SerializedName("id")
    @Expose
    Long id;
    @SerializedName("listed_count")
    @Expose
    Integer listedCount;
    @SerializedName("profile_use_background_image")
    @Expose
    Boolean profileUseBackgroundImage;
    @SerializedName("profile_text_color")
    @Expose
    String profileTextColor;
    @SerializedName("followers_count")
    @Expose
    Integer followersCount;
    @SerializedName("lang")
    @Expose
    String lang;
    @SerializedName("protected")
    @Expose
    Boolean _protected;
    @SerializedName("geo_enabled")
    @Expose
    Boolean geoEnabled;
    @SerializedName("notifications")
    @Expose
    Boolean notifications;
    @SerializedName("description")
    @Expose
    String description;
    @SerializedName("profile_background_color")
    @Expose
    String profileBackgroundColor;
    @SerializedName("verified")
    @Expose
    Boolean verified;
    @SerializedName("time_zone")
    @Expose
    String timeZone;
    @SerializedName("profile_background_image_url_https")
    @Expose
    String profileBackgroundImageUrlHttps;
    @SerializedName("statuses_count")
    @Expose
    Integer statusesCount;
    @SerializedName("profile_background_image_url")
    @Expose
    String profileBackgroundImageUrl;
    @SerializedName("default_profile_image")
    @Expose
    Boolean defaultProfileImage;
    @SerializedName("friends_count")
    @Expose
    Integer friendsCount;
    @SerializedName("following")
    @Expose
    Boolean following;
    @SerializedName("show_all_inline_media")
    @Expose
    Boolean showAllInlineMedia;
    @SerializedName("screen_name")
    @Expose
    String screenName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileSidebarFillColor() {
        return profileSidebarFillColor;
    }

    public void setProfileSidebarFillColor(String profileSidebarFillColor) {
        this.profileSidebarFillColor = profileSidebarFillColor;
    }

    public Boolean getProfileBackgroundTile() {
        return profileBackgroundTile;
    }

    public void setProfileBackgroundTile(Boolean profileBackgroundTile) {
        this.profileBackgroundTile = profileBackgroundTile;
    }

    public String getProfileSidebarBorderColor() {
        return profileSidebarBorderColor;
    }

    public void setProfileSidebarBorderColor(String profileSidebarBorderColor) {
        this.profileSidebarBorderColor = profileSidebarBorderColor;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getFollowRequestSent() {
        return followRequestSent;
    }

    public void setFollowRequestSent(Boolean followRequestSent) {
        this.followRequestSent = followRequestSent;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public Boolean getIsTranslator() {
        return isTranslator;
    }

    public void setIsTranslator(Boolean isTranslator) {
        this.isTranslator = isTranslator;
    }

    public String getProfileLinkColor() {
        return profileLinkColor;
    }

    public void setProfileLinkColor(String profileLinkColor) {
        this.profileLinkColor = profileLinkColor;
    }

    public UserEntities getEntities() {
        return entities;
    }

    public void setEntities(UserEntities entities) {
        this.entities = entities;
    }

    public Boolean getDefaultProfile() {
        return defaultProfile;
    }

    public void setDefaultProfile(Boolean defaultProfile) {
        this.defaultProfile = defaultProfile;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getContributorsEnabled() {
        return contributorsEnabled;
    }

    public void setContributorsEnabled(Boolean contributorsEnabled) {
        this.contributorsEnabled = contributorsEnabled;
    }

    public Integer getFavouritesCount() {
        return favouritesCount;
    }

    public void setFavouritesCount(Integer favouritesCount) {
        this.favouritesCount = favouritesCount;
    }

    public Integer getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(Integer utcOffset) {
        this.utcOffset = utcOffset;
    }

    public String getProfileImageUrlHttps() {
        return profileImageUrlHttps;
    }

    public void setProfileImageUrlHttps(String profileImageUrlHttps) {
        this.profileImageUrlHttps = profileImageUrlHttps;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getListedCount() {
        return listedCount;
    }

    public void setListedCount(Integer listedCount) {
        this.listedCount = listedCount;
    }

    public Boolean getProfileUseBackgroundImage() {
        return profileUseBackgroundImage;
    }

    public void setProfileUseBackgroundImage(Boolean profileUseBackgroundImage) {
        this.profileUseBackgroundImage = profileUseBackgroundImage;
    }

    public String getProfileTextColor() {
        return profileTextColor;
    }

    public void setProfileTextColor(String profileTextColor) {
        this.profileTextColor = profileTextColor;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Boolean getProtected() {
        return _protected;
    }

    public void setProtected(Boolean _protected) {
        this._protected = _protected;
    }

    public Boolean getGeoEnabled() {
        return geoEnabled;
    }

    public void setGeoEnabled(Boolean geoEnabled) {
        this.geoEnabled = geoEnabled;
    }

    public Boolean getNotifications() {
        return notifications;
    }

    public void setNotifications(Boolean notifications) {
        this.notifications = notifications;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileBackgroundColor() {
        return profileBackgroundColor;
    }

    public void setProfileBackgroundColor(String profileBackgroundColor) {
        this.profileBackgroundColor = profileBackgroundColor;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getProfileBackgroundImageUrlHttps() {
        return profileBackgroundImageUrlHttps;
    }

    public void setProfileBackgroundImageUrlHttps(String profileBackgroundImageUrlHttps) {
        this.profileBackgroundImageUrlHttps = profileBackgroundImageUrlHttps;
    }

    public Integer getStatusesCount() {
        return statusesCount;
    }

    public void setStatusesCount(Integer statusesCount) {
        this.statusesCount = statusesCount;
    }

    public String getProfileBackgroundImageUrl() {
        return profileBackgroundImageUrl;
    }

    public void setProfileBackgroundImageUrl(String profileBackgroundImageUrl) {
        this.profileBackgroundImageUrl = profileBackgroundImageUrl;
    }

    public Boolean getDefaultProfileImage() {
        return defaultProfileImage;
    }

    public void setDefaultProfileImage(Boolean defaultProfileImage) {
        this.defaultProfileImage = defaultProfileImage;
    }

    public Integer getFriendsCount() {
        return friendsCount;
    }

    public void setFriendsCount(Integer friendsCount) {
        this.friendsCount = friendsCount;
    }

    public Boolean getFollowing() {
        return following;
    }

    public void setFollowing(Boolean following) {
        this.following = following;
    }

    public Boolean getShowAllInlineMedia() {
        return showAllInlineMedia;
    }

    public void setShowAllInlineMedia(Boolean showAllInlineMedia) {
        this.showAllInlineMedia = showAllInlineMedia;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

}
