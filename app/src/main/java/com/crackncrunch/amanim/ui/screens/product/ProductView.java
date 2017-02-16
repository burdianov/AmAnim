package com.crackncrunch.amanim.ui.screens.product;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.ViewAnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crackncrunch.amanim.R;
import com.crackncrunch.amanim.data.storage.dto.ProductDto;
import com.crackncrunch.amanim.data.storage.dto.ProductLocalInfo;
import com.crackncrunch.amanim.di.DaggerService;
import com.crackncrunch.amanim.mvp.views.AbstractView;
import com.crackncrunch.amanim.mvp.views.IProductView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ProductView extends AbstractView<ProductScreen.ProductPresenter>
        implements IProductView {

    public static final String TAG = "ProductView";

    @BindView(R.id.product_name_txt)
    TextView mProductNameTxt;
    @BindView(R.id.product_description_txt)
    TextView mProductDescriptionTxt;
    @BindView(R.id.product_image)
    ImageView mProductImage;
    @BindView(R.id.product_count_txt)
    TextView mProductCountTxt;
    @BindView(R.id.product_price_txt)
    TextView mProductPriceTxt;
    @BindView(R.id.favorite_btn)
    CheckBox mFavoriteBtn;
    @BindView(R.id.product_wrapper)
    LinearLayout productWrapper;

    @Inject
    Picasso mPicasso;

    public ProductView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<ProductScreen.Component>getDaggerComponent(context).inject(this);
    }

    //region ==================== IProductView ===================

    @Override
    public void showProductView(final ProductDto product) {
        mProductNameTxt.setText(product.getProductName());
        mProductDescriptionTxt.setText(product.getDescription());
        mProductCountTxt.setText(String.valueOf(product.getCount()));
        if (product.getCount() > 0) {
            mProductPriceTxt.setText(String.valueOf(product.getCount() * product
                    .getPrice() + ".-"));
        } else {
            mProductPriceTxt.setText(String.valueOf(product.getPrice() + ".-"));
        }
        mFavoriteBtn.setChecked(product.isFavorite());

        mPicasso.load(product.getImageUrl())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .fit()
                .centerInside()
                .placeholder(R.drawable.placeholder)
                .into(mProductImage, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        mPicasso.load(product.getImageUrl())
                                .fit()
                                .centerInside()
                                .placeholder(R.drawable.placeholder)
                                .into(mProductImage);
                    }
                });
    }

    public ProductLocalInfo getProductLocalInfo() {
        return new ProductLocalInfo(0, mFavoriteBtn.isChecked(), Integer.parseInt
                (mProductCountTxt.getText().toString()));
    }

    @Override
    public void updateProductCountView(ProductDto product) {
        mProductCountTxt.setText(String.valueOf(product.getCount()));
        if (product.getCount() >= 0) {
            mProductPriceTxt.setText(String.valueOf(product.getCount() * product
                    .getPrice() + ".-"));
        }
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    //endregion

    //region ==================== Events ===================

    @OnClick(R.id.plus_btn)
    void clickPlus() {
        mPresenter.clickOnPlus();
    }

    @OnClick(R.id.minus_btn)
    void clickMinus() {
        mPresenter.clickOnMinus();
    }

    @OnClick(R.id.favorite_btn)
    void clickOnFavorite() {
        mPresenter.clickFavorite();
        startAddToCartAnim(); // for testing purposes only
    }

    @OnClick(R.id.show_more_btn)
    void clickOnShowMore() {
        mPresenter.clickShowMore();
    }

    //endregion

    //region ==================== Animation ===================

    public void startAddToCartAnim() {
        final int cx = (productWrapper.getLeft() + productWrapper.getRight()) / 2; // вычисляем центр карточки по X
        final int cy = (productWrapper.getTop() + productWrapper.getBottom()) / 2; // вычисляем центр карточки по Y
        final int radius = Math.max(productWrapper.getWidth(), productWrapper.getHeight()); // вычисляем радиус (максимальное значение высоты или ширины карточки)

        final Animator hideCircleAnim;
        final Animator showCircleAnim;
        Animator hideColorAnim = null;
        Animator showColorAnim = null;

        // VERY IMPORTANT!!! Reveal Animation must always be re-created!!!
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            hideCircleAnim = ViewAnimationUtils.createCircularReveal
                    (productWrapper, cx, cy, radius, 0); // создаем анимацию  для объекта карточка, из центра, с максимального радиуса до 0
            hideCircleAnim.addListener(new AnimatorListenerAdapter() { // вешаем слушатель на на окончание анимации, когда анимация  заканчивается, делаем карточку невидимой
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    productWrapper.setVisibility(INVISIBLE);
                }
            });

            showCircleAnim = ViewAnimationUtils.createCircularReveal
                    (productWrapper, cx, cy, 0, radius);
            showCircleAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationEnd(animation);
                    productWrapper.setVisibility(VISIBLE);
                }
            });

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

                ColorDrawable cdr = (ColorDrawable) productWrapper.getForeground();

                hideColorAnim = ObjectAnimator.ofArgb(cdr, "color",
                        getResources().getColor(R.color.colorAccent, null));
                showColorAnim = ObjectAnimator.ofArgb(cdr, "color",
                        getResources().getColor(R.color.transparent, null));
            }
        } else {
            // TODO: 15-Feb-17 implement a prettier animation for old versions
            hideCircleAnim = ObjectAnimator.ofFloat(productWrapper, "alpha", 0);
            showCircleAnim = ObjectAnimator.ofFloat(productWrapper, "alpha", 1);
        }

        AnimatorSet hideSet = new AnimatorSet();
        AnimatorSet showSet = new AnimatorSet();
        AnimatorSet resultSet = new AnimatorSet();

        addAnimatorTogetherInSet(hideSet, hideCircleAnim, hideColorAnim); // добавляем аниматоры в сет (если они не null)
        addAnimatorTogetherInSet(showSet, showCircleAnim, showColorAnim);
        hideSet.setDuration(600); // устанавливаем длительность анимации
        hideSet.setInterpolator(new FastOutSlowInInterpolator()); // устанавливаем временную функцию (интерполятор) анимации

        showSet.setStartDelay(1000); // устанавливаем задержку анимации появления
        showSet.setDuration(600);
        showSet.setInterpolator(new FastOutSlowInInterpolator());

        resultSet.playSequentially(hideSet, showSet); // результирующий сет последовательно проигрывает анимации скрытия и появления
        resultSet.start();

        /*showCircleAnim.setStartDelay(1000);
        hideSet.playSequentially(hideCircleAnim, showCircleAnim);*/
        // hideSet.start();
    }

    private void addAnimatorTogetherInSet(AnimatorSet set, Animator... anims) {
        ArrayList<Animator> animatorList = new ArrayList<>();
        for (Animator animator : anims) {
            if (animator != null) {
                animatorList.add(animator);
            }
        }
        set.playTogether(animatorList);
    }

    //endregion
}
