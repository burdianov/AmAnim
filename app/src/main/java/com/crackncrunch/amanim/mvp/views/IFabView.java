package com.crackncrunch.amanim.mvp.views;

import android.view.View;

public interface IFabView {
    void setFab(boolean isVisible, int icon, View.OnClickListener onClickListener);
    void removeFab();
}
