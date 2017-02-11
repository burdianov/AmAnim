package com.crackncrunch.amanim.ui.screens.product_details;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.squareup.picasso.Picasso;
import com.crackncrunch.amanim.R;
import com.crackncrunch.amanim.data.storage.realm.ProductRealm;
import com.crackncrunch.amanim.di.DaggerService;
import com.crackncrunch.amanim.di.scopes.DaggerScope;
import com.crackncrunch.amanim.flow.AbstractScreen;
import com.crackncrunch.amanim.flow.Screen;
import com.crackncrunch.amanim.mvp.models.DetailModel;
import com.crackncrunch.amanim.mvp.presenters.AbstractPresenter;
import com.crackncrunch.amanim.mvp.presenters.MenuItemHolder;
import com.crackncrunch.amanim.mvp.presenters.RootPresenter;
import com.crackncrunch.amanim.ui.screens.catalog.CatalogScreen;
import com.crackncrunch.amanim.ui.screens.product_details.comments.AddCommentScreen;

import dagger.Provides;
import flow.Flow;
import flow.TreeKey;
import mortar.MortarScope;

@Screen(R.layout.screen_detail)
public class DetailScreen extends AbstractScreen<CatalogScreen.Component>
        implements TreeKey {

    private final ProductRealm mProductRealm;

    public DetailScreen(ProductRealm product) {
        mProductRealm = product;
    }

    @Override
    public Object createScreenComponent(CatalogScreen.Component parentComponent) {
        return DaggerDetailScreen_Component.builder()
                .component(parentComponent)
                .module(new Module())
                .build();
    }

    @NonNull
    @Override
    public Object getParentKey() {
        return new CatalogScreen();
    }

    //region ==================== DI ===================

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(DetailScreen.class)
        DetailPresenter provideDetailPresenter() {
            return new DetailPresenter(mProductRealm);
        }

        @Provides
        @DaggerScope(DetailScreen.class)
        DetailModel provideDetailModel() {
            return new DetailModel();
        }
    }

    @dagger.Component(dependencies = CatalogScreen.Component.class, modules =
            Module.class)
    @DaggerScope(DetailScreen.class)
    public interface Component {
        void inject(DetailPresenter presenter);
        void inject(DetailView view);

        DetailModel getDetailModel();
        RootPresenter getRootPresenter();
        Picasso getPicasso();
    }

    //endregion

    //region ==================== Presenter ===================

    public class DetailPresenter extends AbstractPresenter<DetailView,
            DetailModel> {
        private final ProductRealm mProduct;

        public DetailPresenter(ProductRealm productRealm) {
            mProduct = productRealm;
        }

        @Override
        protected void initActionBar() {
            mRootPresenter.newActionBarBuilder()
                    .setTitle(mProduct.getProductName())
                    .setBackArrow(true)
                    .addAction(new MenuItemHolder("Add to Cart", R.drawable
                            .ic_shopping_basket_black_24dp, item -> {
                        getRootView().showMessage("Go to Cart");
                        return true;
                    }))
                    .setTab(getView().getViewPager())
                    .build();
        }

        @Override
        protected void initFab() {
            // empty
        }

        protected void initFab(int page) {
            switch (page) {
                case 0:
                    mRootPresenter.newFabBuilder()
                            .setIcon(R.drawable.ic_favorite_white_24dp)
                            .setVisible(true)
                            .setOnClickListener(v -> getRootView().showMessage
                                    ("Favorite button clicked")).build();
                    break;
                case 1:
                    mRootPresenter.newFabBuilder()
                            .setIcon(R.drawable.ic_add_white_24dp)
                            .setVisible(true)
                            .setOnClickListener(v -> {
                                if (getView() != null) {
                                    Flow.get(getView()).set(new AddCommentScreen(mProduct));
                                }
                            })
                            .build();
                    break;
            }
        }

        @Override
        public void dropView(DetailView view) {
            super.dropView(view);
        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (getView() != null) {
                getView().initView(mProduct);
            }
        }
    }

    //endregion
}