package com.crackncrunch.amanim.mvp.views;

import android.support.v4.view.ViewPager;

import com.crackncrunch.amanim.mvp.presenters.MenuItemHolder;

import java.util.List;

public interface IActionBarView {
    void setTitle(CharSequence title);
    void setVisible(boolean visible);
    void setBackArrow(boolean enabled);
    void setMenuItem(List<MenuItemHolder> items);
    void setTabLayout(ViewPager pager);
    void removeTabLayout();
}
