package com.crackncrunch.amanim.ui.screens.auth;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.crackncrunch.amanim.R;
import com.crackncrunch.amanim.di.DaggerService;
import com.crackncrunch.amanim.mvp.views.AbstractView;
import com.crackncrunch.amanim.mvp.views.IAuthView;
import com.crackncrunch.amanim.utils.FieldsValidator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import flow.Flow;

import static com.crackncrunch.amanim.utils.ConstantsManager.CUSTOM_FONTS_ROOT;
import static com.crackncrunch.amanim.utils.ConstantsManager.CUSTOM_FONT_NAME;

public class AuthView extends AbstractView<AuthScreen.AuthPresenter> implements IAuthView {

    public static final int LOGIN_STATE = 0;
    public static final int IDLE_STATE = 1;

    @Inject
    AuthScreen.AuthPresenter mPresenter;

    @BindView(R.id.auth_card)
    CardView mAuthCard;
    @BindView(R.id.show_catalog_btn)
    Button mShowCatalogBtn;
    @BindView(R.id.login_btn)
    Button mLoginBtn;
    @BindView(R.id.app_name_txt)
    TextView mAppNameTxt;
    @BindView(R.id.login_email_et)
    EditText mEmailEt;
    @BindView(R.id.login_password_et)
    EditText mPasswordEt;
    @BindView(R.id.fb_social_btn)
    ImageButton mFacebook;
    @BindView(R.id.twitter_social_btn)
    ImageButton mTwitter;
    @BindView(R.id.vk_social_btn)
    ImageButton mVK;

    private AuthScreen mScreen;
    private FieldsValidator mEmailValidator;

    public AuthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            mScreen = Flow.getKey(this);
            DaggerService.<AuthScreen.Component>getDaggerComponent(context)
                    .inject(this);
        }

        // TODO: 25-Nov-16 get mScreen and dagger component
    }

    @Override
    protected void initDagger(Context context) {
        // empty
    }

    //region ==================== flow view lifecycle callbacks ===================

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        if (!isInEditMode()) {
            showViewFromState();
        }

        mEmailValidator = new FieldsValidator(getContext(), mEmailEt, null);
        mEmailEt.addTextChangedListener(mEmailValidator);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            mPresenter.takeView(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!isInEditMode()) {
            mPresenter.dropView(this);
        }
    }

    //endregion

    private void showViewFromState() {
        if (mScreen.getCustomState() == LOGIN_STATE) {
            showLoginState();
        } else {
            showIdleState();
        }
    }

    private void showLoginState() {
        /*Animation animation = new ScaleAnimation(
                1.0f, 1.0f,
                0.3f, 1.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 1.0f
        );
        animation.setDuration(500);
        mAuthCard.startAnimation(animation);
*/
        mAuthCard.setVisibility(VISIBLE);
        mShowCatalogBtn.setVisibility(GONE);
    }

    private void showIdleState() {
       /* Animation animation = new ScaleAnimation(
                1.0f, 1.0f,
                1.0f, 0.3f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 1.0f
        );
        animation.setDuration(500);
        mAuthCard.setAnimation(animation);*/

        mAuthCard.setVisibility(GONE);
        mShowCatalogBtn.setVisibility(VISIBLE);
    }

    //region ==================== Events ===================

    @OnClick(R.id.login_btn)
    void loginClick() {
        mPresenter.clickOnLogin();
    }

    @OnClick(R.id.fb_social_btn)
    void fbClick() {
        mPresenter.clickOnFb();
    }

    @OnClick(R.id.twitter_social_btn)
    void twitterClick() {
        mPresenter.clickOnTwitter();
    }

    @OnClick(R.id.vk_social_btn)
    void vkClick() {
        mPresenter.clickOnVk();
    }

    @OnClick(R.id.show_catalog_btn)
    void catalogClick() {
        mPresenter.clickOnShowCatalog();
    }

    //endregion

    //region ==================== IAuthView ===================

    @Override
    public void showLoginBtn() {
        mLoginBtn.setVisibility(VISIBLE);
    }

    @Override
    public void hideLoginBtn() {
        mLoginBtn.setVisibility(GONE);
    }

    @Override
    public void showCatalogScreen() {
        mPresenter.clickOnShowCatalog();
    }

    @Override
    public void requestEmailFocus() {

    }

    @Override
    public void requestPasswordFocus() {

    }

    @Override
    public void animateSocialButtons() {

    }

    @Override
    public void setTypeface() {
        mAppNameTxt.setTypeface(Typeface.createFromAsset(getContext().getAssets(),
                CUSTOM_FONTS_ROOT + CUSTOM_FONT_NAME));
    }

    @Override
    public void addChangeTextListeners() {

    }

    @Override
    public String getUserEmail() {
        return String.valueOf(mEmailEt.getText());
    }

    @Override
    public String getUserPassword() {
        return String.valueOf(mPasswordEt.getText());
    }

    @Override
    public boolean isIdle() {
        return mScreen.getCustomState() == IDLE_STATE;
    }

    @Override
    public void setCustomState(int state) {
        mScreen.setCustomState(state);
        showViewFromState();
    }

    @Override
    public boolean viewOnBackPressed() {
        if (!isIdle()) {
            setCustomState(IDLE_STATE);
            return true;
        } else {
            return false;
        }
    }

    //endregion

    @OnClick(R.id.logo_img)
    void test() {
        invalidLoginAnimation();
    }

    //region ==================== Animation ===================

    private void invalidLoginAnimation() {

        AnimatorSet set = (AnimatorSet) AnimatorInflater
                .loadAnimator(getContext(), R.animator.invalid_field_animator);
        set.setTarget(mShowCatalogBtn);
        set.start();

        //region ==================== For future reference ===================

         /*ObjectAnimator oa = ObjectAnimator.ofFloat(mLoginBtn, "rotationY", 2f);
        ObjectAnimator ob = ObjectAnimator.ofFloat(mLoginBtn, "rotationY", -2f);
        ObjectAnimator oc = ObjectAnimator.ofFloat(mLoginBtn, "rotationY", 0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(oa, ob, oc);
        animatorSet.setDuration(300);
        animatorSet.start();*/

        //endregion
    }

    //endregion
}
