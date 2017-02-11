package com.crackncrunch.amanim.ui.screens.product_details;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.crackncrunch.amanim.R;
import com.crackncrunch.amanim.data.storage.realm.ProductRealm;
import com.crackncrunch.amanim.di.DaggerService;
import com.crackncrunch.amanim.mvp.views.AbstractView;

import butterknife.BindView;

public class DetailView extends AbstractView<DetailScreen.DetailPresenter> {

    @BindView(R.id.detail_pager)
    protected ViewPager mViewPager;

    public DetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<DetailScreen.Component>getDaggerComponent(context).inject
                (this);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    public void initView(ProductRealm product) {
        DetailAdapter adapter = new DetailAdapter(product);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mPresenter.initFab(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
