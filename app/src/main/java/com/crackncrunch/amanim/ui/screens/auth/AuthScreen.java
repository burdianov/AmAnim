package com.crackncrunch.amanim.ui.screens.auth;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.crackncrunch.amanim.R;
import com.crackncrunch.amanim.data.storage.realm.ProductRealm;
import com.crackncrunch.amanim.di.DaggerService;
import com.crackncrunch.amanim.di.scopes.AuthScope;
import com.crackncrunch.amanim.flow.AbstractScreen;
import com.crackncrunch.amanim.flow.Screen;
import com.crackncrunch.amanim.mvp.models.AuthModel;
import com.crackncrunch.amanim.mvp.presenters.AbstractPresenter;
import com.crackncrunch.amanim.mvp.presenters.IAuthPresenter;
import com.crackncrunch.amanim.mvp.presenters.RootPresenter;
import com.crackncrunch.amanim.mvp.views.IRootView;
import com.crackncrunch.amanim.ui.activities.RootActivity;
import com.crackncrunch.amanim.ui.activities.SplashActivity;
import com.crackncrunch.amanim.utils.CredentialsValidator;

import javax.inject.Inject;

import dagger.Provides;
import mortar.MortarScope;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

@Screen(R.layout.screen_auth)
public class AuthScreen extends AbstractScreen<RootActivity.RootComponent> {

    private int mCustomState = 1;

    public void setCustomState(int customState) {
        mCustomState = customState;
    }

    public int getCustomState() {
        return mCustomState;
    }

    //region ==================== Flow & Mortar ===================

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentRootComponent) {
        return DaggerAuthScreen_Component.builder()
                .rootComponent(parentRootComponent)
                .module(new Module())
                .build();
    }

    //endregion

    //region ==================== DI ===================

    @dagger.Module
    public class Module {

        @Provides
        @AuthScope
        AuthPresenter providePresenter() {
            return new AuthPresenter();
        }

        @Provides
        @AuthScope
        AuthModel provideAuthModel() {
            return new AuthModel();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules =
            Module.class)
    @AuthScope
    public interface Component {
        void inject(AuthPresenter presenter);
        void inject(AuthView view);
    }

    //endregion

    //region ==================== Presenter ===================

    public class AuthPresenter extends AbstractPresenter<AuthView, AuthModel>
            implements IAuthPresenter {

        @Inject
        AuthModel mModel;
        @Inject
        RootPresenter mRootPresenter;

        @Override
        protected void initActionBar() {
            // empty
        }

        @Override
        protected void initFab() {
            // empty
        }

        @Override
        protected void initDagger(MortarScope scope) {
            // empty
        }

        @Override
        protected void onEnterScope(MortarScope scope) {
            super.onEnterScope(scope);
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);

            if (getView() != null) {
                if (checkUserAuth()) {
                    getView().hideLoginBtn();
                } else {
                    getView().showLoginBtn();
                }
                getView().setTypeface();
            }
            mCompSubs.add(subscribeOnProductRealmObs());
        }

        private Subscription subscribeOnProductRealmObs() {
            if (getRootView() != null) {
                getRootView().showLoad();
            }
            return mModel.getProductObsFromNetwork()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new RealmSubscriber());
        }

        private class RealmSubscriber extends Subscriber<ProductRealm> {

            @Override
            public void onCompleted() {
                getRootView().hideLoad();
            }

            @Override
            public void onError(Throwable e) {
                if (getRootView() != null) {
                    getRootView().showError(e);
                }
            }

            @Override
            public void onNext(ProductRealm productRealm) {

            }
        }

        @Nullable
        protected IRootView getRootView() {
            return mRootPresenter.getRootView();
        }

        @Override
        public void clickOnLogin() {
            if (getView() != null && getRootView() != null) {
                if (getView().isIdle()) {
                    getView().setCustomState(AuthView.LOGIN_STATE);
                } else {
                    String email = getView().getUserEmail();
                    String password = getView().getUserPassword();

                    if (!CredentialsValidator.isValidEmail(email)) {
                        getView().requestEmailFocus();
//                    getView().showMessage(sAppContext.getString(R.string.err_msg_email));
                        return;
                    }
                    if (!CredentialsValidator.isValidPassword(password)) {
                        getView().requestPasswordFocus();
//                    getView().showMessage(sAppContext.getString(R.string.err_msg_password));
                        return;
                    }
                    mModel.loginUser(email, password);
                    getRootView().showLoad();
                    getRootView().showMessage("request for user auth");

                    Handler handler = new Handler();
                    handler.postDelayed(() -> getRootView().hideLoad(), 3000);
                }
            }
        }

        @Override
        public void clickOnFb() {
            if (getRootView() != null) {
                getRootView().showMessage("clickOnFb");
            }
        }

        @Override
        public void clickOnVk() {
            if (getRootView() != null) {
                getRootView().showMessage("clickOnVk");
            }
        }

        @Override
        public void clickOnTwitter() {
            if (getRootView() != null) {
                getRootView().showMessage("clickOnTwitter");
            }
        }

        @Override
        public void clickOnShowCatalog() {
            if (getView() != null && getRootView() != null) {
//                getRootView().showMessage("Show catalog");

                if (getRootView() instanceof SplashActivity) {
                    ((SplashActivity) getRootView()).startRootActivity();
                } else {
                    // TODO: 27-Nov-16 show catalog screen
                }
            }
        }

        @Override
        public boolean checkUserAuth() {
            return mModel.isAuthUser();
        }
    }
    //endregion


}
