package com.crackncrunch.amanim.di.modules;

import com.crackncrunch.amanim.di.scopes.RootScope;
import com.crackncrunch.amanim.mvp.models.AccountModel;
import com.crackncrunch.amanim.mvp.presenters.RootPresenter;

import dagger.Provides;

@dagger.Module
public class RootModule {
    @Provides
    @RootScope
    RootPresenter provideRootPresenter() {
        return new RootPresenter();
    }

    @Provides
    @RootScope
    AccountModel provideAccountModel() {
        return new AccountModel();
    }
}
