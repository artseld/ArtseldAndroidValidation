package com.artseld.android.validation.validator;

import android.content.Context;
import com.artseld.android.validation.R;

public abstract class AbstractValidator {

    private String mErrorMessage;

    protected Context mContext;

    public AbstractValidator(Context context) {
        mContext = context;
        setMessage(R.string.validation_invalid);
    }

    public AbstractValidator(Context context, int messageId) {
        mContext = context;
        setMessage(messageId);
    }

    public AbstractValidator(Context context, String messageText) {
        mContext = context;
        setMessage(messageText);
    }

    public abstract boolean isValid(Object value);

    public String getMessage() {
        return mErrorMessage;
    }

    public void setMessage(int messageId) {
        mErrorMessage = mContext.getResources().getString(messageId);
    }

    public void setMessage(String messageText) {
        mErrorMessage = messageText;
    }

}
