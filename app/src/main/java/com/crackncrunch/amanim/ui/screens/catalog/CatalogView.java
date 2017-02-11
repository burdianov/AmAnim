package com.crackncrunch.amanim.ui.screens.catalog;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.Button;

import com.crackncrunch.amanim.R;
import com.crackncrunch.amanim.di.DaggerService;
import com.crackncrunch.amanim.mvp.views.AbstractView;
import com.crackncrunch.amanim.mvp.views.ICatalogView;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class CatalogView extends AbstractView<CatalogScreen.CatalogPresenter>
        implements ICatalogView {

    @BindView(R.id.add_to_card_btn)
    Button mAddToCartBtn;
    @BindView(R.id.product_pager)
    ViewPager mProductPager;
    @BindView(R.id.indicator)
    CircleIndicator mIndicator;
    private CatalogAdapter mAdapter;

    public CatalogView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<CatalogScreen.Component>getDaggerComponent(context).inject
                (this);
        mAdapter = new CatalogAdapter();
    }

    @Override
    public void showCatalogView() {
        mProductPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mProductPager);
        mAdapter.registerDataSetObserver(mIndicator.getDataSetObserver());
    }

    @Override
    public void updateProductCounter() {
        // TODO: 28-Oct-16 update count product on cart icon
    }

    public int getCurrentPagerPosition() {
        return mProductPager.getCurrentItem();
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    public CatalogAdapter getAdapter() {
        return mAdapter;
    }

    //region ==================== Events ===================

    @OnClick(R.id.add_to_card_btn)
    void clickAddToCart() {
        mPresenter.clickOnBuyButton(mProductPager.getCurrentItem());
    }

    //endregion
}
