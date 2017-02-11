package com.crackncrunch.amanim.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.crackncrunch.amanim.BuildConfig;
import com.crackncrunch.amanim.R;
import com.crackncrunch.amanim.data.storage.dto.UserInfoDto;
import com.crackncrunch.amanim.di.DaggerService;
import com.crackncrunch.amanim.flow.TreeKeyDispatcher;
import com.crackncrunch.amanim.mortar.ScreenScoper;
import com.crackncrunch.amanim.mvp.presenters.RootPresenter;
import com.crackncrunch.amanim.mvp.views.IRootView;
import com.crackncrunch.amanim.mvp.views.IView;
import com.crackncrunch.amanim.ui.screens.auth.AuthScreen;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import flow.Flow;
import mortar.MortarScope;
import mortar.bundler.BundleServiceRunner;

public class SplashActivity extends AppCompatActivity implements IRootView {

    @Inject
    RootPresenter mRootPresenter;

    @BindView(R.id.coordinator_container)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.root_frame)
    FrameLayout mRootFrame;

    protected ProgressDialog mProgressDialog;

    @Override
    protected void attachBaseContext(Context newBase) {
        newBase = Flow.configure(newBase, this)
                .defaultKey(new AuthScreen())
                .dispatcher(new TreeKeyDispatcher(this))
                .install();
        super.attachBaseContext(newBase);
    }

    @Override
    public Object getSystemService(String name) {
        MortarScope rootActivityScope = MortarScope.findChild
                (getApplicationContext(), RootActivity.class.getName());
        return rootActivityScope.hasService(name) ? rootActivityScope.getService
                (name) : super.getSystemService(name);
    }

    //region ========== Life cycle ==========

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BundleServiceRunner.getBundleServiceRunner(this).onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        DaggerService.<RootActivity.RootComponent>getDaggerComponent(this).inject(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        BundleServiceRunner.getBundleServiceRunner(this).onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        mRootPresenter.takeView(this);
        super.onStart();
    }

    @Override
    protected void onStop() {
        mRootPresenter.dropView(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (isFinishing()) {
            ScreenScoper.destroyScreenScope(AuthScreen.class.getName());
        }
        super.onDestroy();
    }

    //endregion

    //region ========== IAuthView ==========

    @Override
    public void showMessage(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        if (BuildConfig.DEBUG) {
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage("Something went wrong, try again later");
            // TODO: 22-Oct-16 send error stacktrace to crashlytics
        }
    }

    @Override
    public void showLoad() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this, R.style.custom_dialog);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable
                    (Color.TRANSPARENT));
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);
        } else {
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.progress_splash);
        }
    }

    @Override
    public void hideLoad() {
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.hide();
            }
        }
    }

    @Nullable
    @Override
    public IView getCurrentScreen() {
        return (IView) mRootFrame.getChildAt(0);
    }

    @Override
    public void initDrawer(UserInfoDto userInfoDto) {

    }

    @Override
    public void changeCart(int resId) {
        // empty
    }

    // TODO: 25-Nov-16 Resolve the commented methods
//    @Override
//    public void animateSocialButtons() {
//        setSocialButtonsAnimation(mFacebook);
//        setSocialButtonsAnimation(mTwitter);
//        setSocialButtonsAnimation(mVK);
//    }

//    @Override
//    public void addChangeTextListeners() {
//        mEmailEt.addTextChangedListener(new CustomTextWatcher(this, mEmailEt,
//                mLoginBtn));
//        mPasswordEt.addTextChangedListener(new CustomTextWatcher(this,
//                mPasswordEt, mLoginBtn));
//    }

//    @Override
//    public void showCatalogScreen() {
//        Intent intent = new Intent(this, RootActivity.class);
//        startActivity(intent);
//        finish();
//    }

    private void setSocialButtonsAnimation(ImageButton button) {
        button.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate().setDuration(200).scaleX(1.1f).scaleY(1.1f);
                    break;

                case MotionEvent.ACTION_UP:
                    v.animate().setDuration(200).scaleX(1.0f).scaleY(1.0f);
                    break;
            }
            return false;
        });
    }

    //endregion

    @Override
    public void onBackPressed() {
        if (getCurrentScreen() != null && !getCurrentScreen().viewOnBackPressed()
                && !Flow.get(this).goBack()) {
            super.onBackPressed();
        }
    }

    // TODO: 25-Nov-16 Resolve the commented methods
//    @Override
//    public void requestEmailFocus() {
//        if (mEmailEt.requestFocus()) {
//            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//            mEmailEt.setSelection(mEmailEt.length());
//        }
//    }
//
//    @Override
//    public void requestPasswordFocus() {
//        if (mPasswordEt.requestFocus()) {
//            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//            mPasswordEt.setSelection(mPasswordEt.length());
//        }
//    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    public void startRootActivity() {
        Intent intent = new Intent(this, RootActivity.class);
        startActivity(intent);
        finish();
    }
}