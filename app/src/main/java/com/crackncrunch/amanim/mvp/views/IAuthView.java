package com.crackncrunch.amanim.mvp.views;

public interface IAuthView extends IView {
    void showLoginBtn();
    void hideLoginBtn();
    void showCatalogScreen();
    void requestEmailFocus();
    void requestPasswordFocus();
    void animateSocialButtons();
    void setTypeface();
    void addChangeTextListeners();
    String getUserEmail();
    String getUserPassword();
    boolean isIdle();
}
