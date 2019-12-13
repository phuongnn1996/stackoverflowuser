
package com.monsanity.sofuserapp.retrofit.response;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReputationItem {

    @SerializedName("items")
    @Expose
    private List<ReputationDetailItem> reputationDetailItems = new ArrayList<>();
    @SerializedName("has_more")
    @Expose
    private Boolean hasMore;
    @SerializedName("quota_max")
    @Expose
    private Integer quotaMax;
    @SerializedName("quota_remaining")
    @Expose
    private Integer quotaRemaining;

    public List<ReputationDetailItem> getReputationDetailItems() {
        return reputationDetailItems;
    }

    public void setReputationDetailItems(List<ReputationDetailItem> reputationDetailItems) {
        this.reputationDetailItems = reputationDetailItems;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public Integer getQuotaMax() {
        return quotaMax;
    }

    public void setQuotaMax(Integer quotaMax) {
        this.quotaMax = quotaMax;
    }

    public Integer getQuotaRemaining() {
        return quotaRemaining;
    }

    public void setQuotaRemaining(Integer quotaRemaining) {
        this.quotaRemaining = quotaRemaining;
    }

}
