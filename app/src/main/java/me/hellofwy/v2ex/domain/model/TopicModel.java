
package me.hellofwy.v2ex.domain.model;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class TopicModel {

    @SerializedName("content")
    private String mContent;
    @SerializedName("content_rendered")
    private String mContentRendered;
    @SerializedName("created")
    private Long mCreated;
    @SerializedName("id")
    private Long mId;
    @SerializedName("last_modified")
    private Long mLastModified;
    @SerializedName("last_touched")
    private Long mLastTouched;
    @SerializedName("member")
    private MemberModel mMember;
    @SerializedName("node")
    private NodeModel mNode;
    @SerializedName("replies")
    private Long mReplies;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("url")
    private String mUrl;

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getContentRendered() {
        return mContentRendered;
    }

    public void setContentRendered(String content_rendered) {
        mContentRendered = content_rendered;
    }

    public Long getCreated() {
        return mCreated;
    }

    public void setCreated(Long created) {
        mCreated = created;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Long getLastModified() {
        return mLastModified;
    }

    public void setLastModified(Long last_modified) {
        mLastModified = last_modified;
    }

    public Long getLastTouched() {
        return mLastTouched;
    }

    public void setLastTouched(Long last_touched) {
        mLastTouched = last_touched;
    }

    public MemberModel getMember() {
        return mMember;
    }

    public void setMember(MemberModel member) {
        mMember = member;
    }

    public NodeModel getNode() {
        return mNode;
    }

    public void setNode(NodeModel node) {
        mNode = node;
    }

    public Long getReplies() {
        return mReplies;
    }

    public void setReplies(Long replies) {
        mReplies = replies;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

}
