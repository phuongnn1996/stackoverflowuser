
package com.monsanity.sofuserapp.retrofit.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetailItem {

    @SerializedName("badge_counts")
    @Expose
    private BadgeCounts badgeCounts;
    @SerializedName("account_id")
    @Expose
    private Integer accountId;
    @SerializedName("is_employee")
    @Expose
    private Boolean isEmployee;
    @SerializedName("last_modified_date")
    @Expose
    private Integer lastModifiedDate;
    @SerializedName("last_access_date")
    @Expose
    private Long lastAccessDate;
    @SerializedName("reputation_change_year")
    @Expose
    private Integer reputationChangeYear;
    @SerializedName("reputation_change_quarter")
    @Expose
    private Integer reputationChangeQuarter;
    @SerializedName("reputation_change_month")
    @Expose
    private Integer reputationChangeMonth;
    @SerializedName("reputation_change_week")
    @Expose
    private Integer reputationChangeWeek;
    @SerializedName("reputation_change_day")
    @Expose
    private Integer reputationChangeDay;
    @SerializedName("reputation")
    @Expose
    private Integer reputation;
    @SerializedName("creation_date")
    @Expose
    private Integer creationDate;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("accept_rate")
    @Expose
    private Integer acceptRate;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("website_url")
    @Expose
    private String websiteUrl;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("display_name")
    @Expose
    private String displayName;

    private boolean isBookmarked;

    public BadgeCounts getBadgeCounts() {
        return badgeCounts;
    }

    public void setBadgeCounts(BadgeCounts badgeCounts) {
        this.badgeCounts = badgeCounts;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Boolean getIsEmployee() {
        return isEmployee;
    }

    public void setIsEmployee(Boolean isEmployee) {
        this.isEmployee = isEmployee;
    }

    public Integer getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Integer lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Long getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(Long lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }

    public Integer getReputationChangeYear() {
        return reputationChangeYear;
    }

    public void setReputationChangeYear(Integer reputationChangeYear) {
        this.reputationChangeYear = reputationChangeYear;
    }

    public Integer getReputationChangeQuarter() {
        return reputationChangeQuarter;
    }

    public void setReputationChangeQuarter(Integer reputationChangeQuarter) {
        this.reputationChangeQuarter = reputationChangeQuarter;
    }

    public Integer getReputationChangeMonth() {
        return reputationChangeMonth;
    }

    public void setReputationChangeMonth(Integer reputationChangeMonth) {
        this.reputationChangeMonth = reputationChangeMonth;
    }

    public Integer getReputationChangeWeek() {
        return reputationChangeWeek;
    }

    public void setReputationChangeWeek(Integer reputationChangeWeek) {
        this.reputationChangeWeek = reputationChangeWeek;
    }

    public Integer getReputationChangeDay() {
        return reputationChangeDay;
    }

    public void setReputationChangeDay(Integer reputationChangeDay) {
        this.reputationChangeDay = reputationChangeDay;
    }

    public Integer getReputation() {
        return reputation;
    }

    public void setReputation(Integer reputation) {
        this.reputation = reputation;
    }

    public Integer getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Integer creationDate) {
        this.creationDate = creationDate;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAcceptRate() {
        return acceptRate;
    }

    public void setAcceptRate(Integer acceptRate) {
        this.acceptRate = acceptRate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }
}
