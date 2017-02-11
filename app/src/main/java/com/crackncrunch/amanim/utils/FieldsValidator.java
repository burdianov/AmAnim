package com.crackncrunch.amanim.utils;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.crackncrunch.amanim.R;

public class FieldsValidator implements TextWatcher {

    private final EditText mEditText;
    private final Context mContext;
    private final Button mLoginButton;
    private TextInputLayout mTextInputLayout;

    public FieldsValidator(Context context, EditText editText, @Nullable Button
            loginButton) {
        mContext = context;
        mEditText = editText;
        mLoginButton = loginButton;
        mTextInputLayout = (TextInputLayout) mEditText.getParent().getParent();
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void afterTextChanged(Editable editable) {
        switch (mEditText.getId()) {
            case R.id.login_email_et:
                validateEmail(String.valueOf(editable));
                break;
            case R.id.login_password_et:
                validatePassword(String.valueOf(editable));
                break;
        }
    }

    // TODO: 22-Oct-16 fine tune the Login Button Enable/Disable behavior

    private void validateEmail(String email) {
        if (!isValidEmail(email) && email.length() != 0) {
            mTextInputLayout.setHint(mContext.getString(R.string.err_msg_email));
//            mTextInputLayout.setErrorEnabled(true);
//            mTextInputLayout.setError(mContext.getString(R.string.err_msg_email));
//            mLoginButton.setEnabled(false);
        } else if (email.length() == 0) {
//            mLoginButton.setEnabled(false);
            mTextInputLayout.setHint(mContext.getString(R.string.email_hint));
        } else {
//            mLoginButton.setEnabled(true);
            mTextInputLayout.setHint(mContext.getString(R.string.email_hint));
        }
    }

    private void validatePassword(String password) {
        if (!isValidPassword(password) && password.length() != 0) {
            mTextInputLayout.setHint(mContext.getString(R.string.err_msg_password));
//            mTextInputLayout.setErrorEnabled(true);
//            mTextInputLayout.setError(mContext.getString(R.string.err_msg_password)
//            mLoginButton.setEnabled(false);
        } else if (password.length() == 0) {
//            mLoginButton.setEnabled(false);
            mTextInputLayout.setHint(mContext.getString(R.string.password_hint));
        } else {
//            mLoginButton.setEnabled(true);
            mTextInputLayout.setHint(mContext.getString(R.string.password_hint));
        }
    }

    private static boolean isValidEmail(String email) {
        return !email.trim().isEmpty() &&
                AppUtils.EMAIL_ADDRESS_VALIDATE.matcher(email.trim()).matches();
    }

    private static boolean isValidPassword(String password) {
        return !password.trim().isEmpty() &&
                AppUtils.PASSWORD_VALIDATE.matcher(password.trim()).matches();
    }
}