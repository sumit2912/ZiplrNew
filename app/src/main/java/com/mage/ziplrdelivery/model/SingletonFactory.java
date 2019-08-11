package com.mage.ziplrdelivery.model;

import com.mage.ziplrdelivery.model.data.DashBoardBean;
import com.mage.ziplrdelivery.model.param.LoginParamBean;

public class SingletonFactory {
    private static SingletonFactory singletonFactory;
    private LoginParamBean loginParamBean;
    private DashBoardBean dashBoardBean;

    public void setSingletonFactory(SingletonFactory sf) {
        singletonFactory = sf;
    }

    public static SingletonFactory getInstance() {
        if (singletonFactory == null) {
            singletonFactory = new SingletonFactory();
        }
        return singletonFactory;
    }

    public LoginParamBean getLoginParamBean() {
        if (loginParamBean == null) {
            loginParamBean = new LoginParamBean();
        }
        return loginParamBean;
    }

    public void setLoginParamBean(LoginParamBean loginParamBean) {
        this.loginParamBean = loginParamBean;
    }

    public DashBoardBean getDashBoardBean() {
        if (dashBoardBean == null) {
            dashBoardBean = new DashBoardBean();
        }
        return this.dashBoardBean;
    }

    public void setDashBoardBean(DashBoardBean dashBoardBean) {
        this.dashBoardBean = dashBoardBean;
    }
}
