package com.crackncrunch.amanim.ui.screens.product_details.comments;

import android.os.Bundle;

import com.crackncrunch.amanim.R;
import com.crackncrunch.amanim.data.storage.dto.CommentDto;
import com.crackncrunch.amanim.data.storage.realm.CommentRealm;
import com.crackncrunch.amanim.data.storage.realm.ProductRealm;
import com.crackncrunch.amanim.di.DaggerService;
import com.crackncrunch.amanim.di.scopes.DaggerScope;
import com.crackncrunch.amanim.flow.AbstractScreen;
import com.crackncrunch.amanim.flow.Screen;
import com.crackncrunch.amanim.mvp.models.DetailModel;
import com.crackncrunch.amanim.mvp.presenters.AbstractPresenter;
import com.crackncrunch.amanim.ui.screens.product_details.DetailScreen;

import java.util.List;

import dagger.Provides;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import mortar.MortarScope;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

@Screen(R.layout.screen_comments)
public class CommentsScreen extends AbstractScreen<DetailScreen.Component> {
    private ProductRealm mProductRealm;

    public CommentsScreen(ProductRealm productRealm) {
        mProductRealm = productRealm;
    }

    @Override
    public Object createScreenComponent(DetailScreen.Component parentComponent) {
        return DaggerCommentsScreen_Component.builder()
                .component(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(CommentsScreen.class)
        CommentsPresenter provideCommentsPresenter() {
            return new CommentsPresenter(mProductRealm);
        }
    }

    @dagger.Component(dependencies = DetailScreen.Component.class,
            modules = Module.class)
    @DaggerScope(CommentsScreen.class)
    public interface Component {
        void inject(CommentsPresenter presenter);
        void inject(CommentsView view);
        void inject(CommentsAdapter adapter);
    }

    //region ==================== Presenter ===================

    public class CommentsPresenter extends AbstractPresenter<CommentsView,
            DetailModel> {

        private final ProductRealm mProduct;
        private RealmChangeListener mListener;

        public CommentsPresenter(ProductRealm productRealm) {
            mProduct = productRealm;
        }

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
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);

            mListener = new RealmChangeListener<ProductRealm>() {
                @Override
                public void onChange(ProductRealm element) {
                    CommentsPresenter.this.updateProductList(element);
                }
            };

            mProduct.addChangeListener(mListener);

            RealmList<CommentRealm> comments = mProduct.getCommentsRealm();
            Observable<CommentDto> commentsObs = Observable.from(comments)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .map(CommentDto::new);

            mCompSubs.add(subscribe(commentsObs, new ViewSubscriber<CommentDto>() {
                @Override
                public void onNext(CommentDto commentDto) {
                    getView().getAdapter().addItem(commentDto);
                }
            }));
            getView().initView();
        }

        @Override
        public void dropView(CommentsView view) {
            mProduct.removeChangeListener(mListener);
            super.dropView(view);
        }

        private void updateProductList(ProductRealm element) {
            Observable<List<CommentDto>> obs = Observable.from(element.getCommentsRealm())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .map(CommentDto::new)
                    .toList();
            mCompSubs.add(subscribe(obs, new ViewSubscriber<List<CommentDto>>() {
                @Override
                public void onNext(List<CommentDto> commentDtos) {
                    getView().getAdapter().reloadAdapter(commentDtos);
                }
            }));
        }
    }

    //endregion
}
