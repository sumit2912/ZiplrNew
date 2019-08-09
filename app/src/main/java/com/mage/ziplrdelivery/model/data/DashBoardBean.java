package com.mage.ziplrdelivery.model.data;

import java.util.List;

public class DashBoardBean {
    private String profileName;
    private String profileEmail;
    private List<NavMenuBean> navMenuList;

    public DashBoardBean(String profileName, String profileEmail, List<NavMenuBean> navMenuList) {
        this.profileName = profileName;
        this.profileEmail = profileEmail;
        this.navMenuList = navMenuList;
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

    public List<NavMenuBean> getNavMenuList() {
        return navMenuList;
    }

    public void setNavMenuList(List<NavMenuBean> navMenuList) {
        this.navMenuList = navMenuList;
    }
}
