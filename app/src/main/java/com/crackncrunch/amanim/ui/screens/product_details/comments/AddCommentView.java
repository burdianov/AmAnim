package com.crackncrunch.amanim.ui.screens.product_details.comments;

import android.content.Context;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;

import com.crackncrunch.amanim.R;
import com.crackncrunch.amanim.data.storage.realm.CommentRealm;
import com.crackncrunch.amanim.di.DaggerService;
import com.crackncrunch.amanim.mvp.views.AbstractView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddCommentView extends AbstractView<AddCommentScreen
        .AddCommentPresenter> {

    @BindView(R.id.save_comment_btn)
    Button mSaveCommentBtn;
    @BindView(R.id.set_rating)
    AppCompatRatingBar mRating;
    @BindView(R.id.add_comment_et)
    EditText mCommentEt;

    @Inject
    AddCommentScreen.AddCommentPresenter mPresenter;

    public AddCommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<AddCommentScreen.Component>getDaggerComponent(context).inject
                (this);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    //region ==================== Events ===================

    @OnClick(R.id.save_comment_btn)
    void saveComment() {
        CommentRealm comment = new CommentRealm(mRating.getRating(),
                mCommentEt.getText().toString());
        mPresenter.addComment(comment);
    }

    //endregion
}
