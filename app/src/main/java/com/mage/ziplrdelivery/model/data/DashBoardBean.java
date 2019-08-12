package com.mage.ziplrdelivery.model.data;

public class DashBoardBean {
    private String profileName;
    private String profileEmail;

    public DashBoardBean() {
    }

    public DashBoardBean(String profileName, String profileEmail) {
        this.profileName = profileName;
        this.profileEmail = profileEmail;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileEmail() {
        return profileEmail;
    }

    public void setProfileEmail(String profileEmail) {
        this.profileEmail = profileEmail;
    }

}
