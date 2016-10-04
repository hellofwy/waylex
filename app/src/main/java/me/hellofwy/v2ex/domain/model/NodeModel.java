
package me.hellofwy.v2ex.domain.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class NodeModel {

    @SerializedName("avatar_large")
    private String mAvatarLarge;
    @SerializedName("avatar_mini")
    private String mAvatarMini;
    @SerializedName("avatar_normal")
    private String mAvatarNormal;
    @SerializedName("created")
    private Long mCreated;
    @SerializedName("footer")
    private Object mFooter;
    @SerializedName("header")
    private String mHeader;
    @SerializedName("id")
    private Long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("stars")
    private Long mStars;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("title_alternative")
    private String mTitleAlternative;
    @SerializedName("topics")
    private Long mTopics;
    @SerializedName("url")
    private String mUrl;

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

    public Long getCreated() {
        return mCreated;
    }

    public void setCreated(Long created) {
        mCreated = created;
    }

    public Object getFooter() {
        return mFooter;
    }

    public void setFooter(Object footer) {
        mFooter = footer;
    }

    public String getHeader() {
        return mHeader;
    }

    public void setHeader(String header) {
        mHeader = header;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Long getStars() {
        return mStars;
    }

    public void setStars(Long stars) {
        mStars = stars;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitleAlternative() {
        return mTitleAlternative;
    }

    public void setTitleAlternative(String title_alternative) {
        mTitleAlternative = title_alternative;
    }

    public Long getTopics() {
        return mTopics;
    }

    public void setTopics(Long topics) {
        mTopics = topics;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

}
