package com.crackncrunch.amanim.mvp.views;

import android.support.annotation.Nullable;

import com.crackncrunch.amanim.data.storage.dto.UserInfoDto;

public interface IRootView extends IView {
    void showMessage(String message);
    void showError(Throwable e);
    void showLoad();
    void hideLoad();

    void initDrawer(UserInfoDto userInfoDto);
    void changeCart(int resId);

    @Nullable
    IView getCurrentScreen();
}
