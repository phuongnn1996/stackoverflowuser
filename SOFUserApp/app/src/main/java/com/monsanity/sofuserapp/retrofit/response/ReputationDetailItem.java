
package com.monsanity.sofuserapp.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReputationDetailItem {

    @SerializedName("reputation_history_type")
    @Expose
    private String reputationHistoryType;
    @SerializedName("reputation_change")
    @Expose
    private Integer reputationChange;
    @SerializedName("post_id")
    @Expose
    private Integer postId;
    @SerializedName("creation_date")
    @Expose
    private Integer creationDate;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public String getReputationHistoryType() {
        return reputationHistoryType;
    }

    public void setReputationHistoryType(String reputationHistoryType) {
        this.reputationHistoryType = reputationHistoryType;
    }

    public Integer getReputationChange() {
        return reputationChange;
    }

    public void setReputationChange(Integer reputationChange) {
        this.reputationChange = reputationChange;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Integer creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
