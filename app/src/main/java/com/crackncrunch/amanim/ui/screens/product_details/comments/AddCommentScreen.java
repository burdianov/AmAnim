package com.crackncrunch.amanim.ui.screens.product_details.comments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.crackncrunch.amanim.BuildConfig;
import com.crackncrunch.amanim.R;
import com.crackncrunch.amanim.data.storage.realm.CommentRealm;
import com.crackncrunch.amanim.data.storage.realm.ProductRealm;
import com.crackncrunch.amanim.di.DaggerService;
import com.crackncrunch.amanim.di.scopes.DaggerScope;
import com.crackncrunch.amanim.flow.AbstractScreen;
import com.crackncrunch.amanim.flow.Screen;
import com.crackncrunch.amanim.mvp.models.DetailModel;
import com.crackncrunch.amanim.mvp.presenters.AbstractPresenter;
import com.crackncrunch.amanim.ui.screens.product_details.DetailScreen;

import dagger.Provides;
import flow.Flow;
import flow.TreeKey;
import io.realm.Realm;
import mortar.MortarScope;

@Screen(R.layout.screen_add_comment)
public class AddCommentScreen extends AbstractScreen<DetailScreen.Component>
        implements TreeKey {

    private final ProductRealm mProductRealm;

    public AddCommentScreen(ProductRealm productRealm) {
        mProductRealm = productRealm;
    }

    @NonNull
    @Override
    public Object getParentKey() {
        return new CommentsScreen(mProductRealm);
    }

    //region ==================== DI ===================

    @Override
    public Object createScreenComponent(DetailScreen.Component parentComponent) {
        return DaggerAddCommentScreen_Component.builder()
                .component(parentComponent)
                .module(new AddCommentScreen.Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(AddCommentScreen.class)
        AddCommentPresenter provideAddCommentPresenter() {
            return new AddCommentPresenter(mProductRealm);
        }
    }

    @dagger.Component(dependencies = DetailScreen.Component.class, modules =
            Module.class)
    @DaggerScope(AddCommentScreen.class)
    public interface Component {
        void inject(AddCommentPresenter presenter);

        void inject(AddCommentView view);
    }

    //endregion

    //region ==================== Presenter ===================

    public class AddCommentPresenter extends AbstractPresenter<AddCommentView,
            DetailModel> {

        private final ProductRealm mProduct;

        public AddCommentPresenter(ProductRealm productRealm) {
            mProduct = productRealm;
        }

        @Override
        protected void initActionBar() {
            // TODO: 09-Jan-17 implement comment addition via actionbar later on
            mRootPresenter.newActionBarBuilder()
                    .setTitle("Rate the Product")
                    /*.addAction(new MenuItemHolder("Save comment", R.drawable
                            .ic_done_orange_24dp, item -> {
                        getRootView().showMessage("Save Comment");
                        return true;
                    }))*/
                    .setBackArrow(true)
                    .build();
        }

        @Override
        protected void initFab() {
            mRootPresenter.newFabBuilder()
                    .setVisible(false)
                    .build();
        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((AddCommentScreen.Component) scope.getService(DaggerService
                    .SERVICE_NAME)).inject(this);
        }

        public void addComment(CommentRealm commentRealm) {
            Context context = getView().getContext();

            if (getView() != null) {
                if (getView().mCommentEt.getText().length() == 0) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    alertDialog.setTitle(context.getString(R.string.empty_field))
                            .setMessage(context.getString(R.string.can_not_be_empty))
                            .setPositiveButton(context.getString(R.string.ok),
                                    (dialog, which) -> {
                                    })
                            .show();
                } else {
                    switch (BuildConfig.FLAVOR) {
                        case "base":
                            mModel.sendComment(mProduct.getId(), commentRealm);
                            break;
                        case "realmMp":
                            Realm realm = Realm.getDefaultInstance();
                            realm.executeTransaction(realm1 -> mProduct.getCommentsRealm().add(commentRealm));
                            realm.close();
                            break;
                    }
                    Flow.get(getView()).goBack();
                }
            }
        }
    }

    //endregion
}
