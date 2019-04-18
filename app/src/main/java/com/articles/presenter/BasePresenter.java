package com.articles.presenter;


import android.content.Context;

import com.articles.BaseApp;
import com.articles.interfaces.IView;

public abstract class BasePresenter<I extends IView> {

    BaseApp apidemo;
    I iView;

    public BasePresenter() {
    }

    public BasePresenter(BaseApp jetSetaCustomerApp) {
        this.apidemo = jetSetaCustomerApp;
    }

    public I getView() {
        return iView;
    }

    public void setView(I iView) {
        this.iView = iView;
    }

    public BaseApp getApplicationClass() {
        return (BaseApp) iView.getContext().getApplicationContext();
    }

    public void handleError(String errorMessage) {
        getView().onError(errorMessage);
    }

    public Context getAppContext() {
        return BaseApp.getAppContext();
    }

}
