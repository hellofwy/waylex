
package me.hellofwy.v2ex.domain.model;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class MemberModel {

    @SerializedName("avatar_large")
    private String mAvatarLarge;
    @SerializedName("avatar_mini")
    private String mAvatarMini;
    @SerializedName("avatar_normal")
    private String mAvatarNormal;
    @SerializedName("bio")
    private String mBio;
    @SerializedName("btc")
    private String mBtc;
    @SerializedName("created")
    private Long mCreated;
    @SerializedName("github")
    private String mGithub;
    @SerializedName("id")
    private Long mId;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("psn")
    private String mPsn;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("tagline")
    private String mTagline;
    @SerializedName("twitter")
    private String mTwitter;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("username")
    private String mUsername;
    @SerializedName("website")
    private String mWebsite;

    public String getAvatarLarge() {
        return mAvatarLarge;
    }

    public void setAvatarLarge(String avatar_large) {
        mAvatarLarge = avatar_large;
    }

    public String getAvatarMini() {
        return mAvatarMini;
    }

    public void setAvatarMini(String avatar_mini) {
        mAvatarMini = avatar_mini;
    }

    public String getAvatarNormal() {
        return mAvatarNormal;
    }

    public void setAvatarNormal(String avatar_normal) {
        mAvatarNormal = avatar_normal;
    }

    public String getBio() {
        return mBio;
    }

    public void setBio(String bio) {
        mBio = bio;
    }

    public String getBtc() {
        return mBtc;
    }

    public void setBtc(String btc) {
        mBtc = btc;
    }

    public Long getCreated() {
        return mCreated;
    }

    public void setCreated(Long created) {
        mCreated = created;
    }

    public String getGithub() {
        return mGithub;
    }

    public void setGithub(String github) {
        mGithub = github;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getPsn() {
        return mPsn;
    }

    public void setPsn(String psn) {
        mPsn = psn;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTagline() {
        return mTagline;
    }

    public void setTagline(String tagline) {
        mTagline = tagline;
    }

    public String getTwitter() {
        return mTwitter;
    }

    public void setTwitter(String twitter) {
        mTwitter = twitter;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public void setWebsite(String website) {
        mWebsite = website;
    }

}
