package com.mage.ziplrdelivery.model;

import com.mage.ziplrdelivery.model.param.LoginParamBean;

public class SingletonFactory {
    private static SingletonFactory singletonFactory;
    private LoginParamBean loginParamBean;

    public static SingletonFactory getInstance(){
        if(singletonFactory == null){
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
}
