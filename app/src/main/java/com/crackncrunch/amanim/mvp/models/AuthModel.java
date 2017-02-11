package com.crackncrunch.amanim.mvp.models;

import com.crackncrunch.amanim.data.managers.DataManager;
import com.crackncrunch.amanim.data.storage.realm.ProductRealm;

import rx.Observable;

public class AuthModel extends AbstractModel {

    public AuthModel() {

    }

    public boolean isAuthUser() {
        return mDataManager.isAuthUser();
    }

    public void loginUser(String email, String password) {
        mDataManager.loginUser(email, password);
    }

    public Observable<ProductRealm> getProductObsFromNetwork() {
        return DataManager.getInstance().getProductsObsFromNetwork();
    }
}
