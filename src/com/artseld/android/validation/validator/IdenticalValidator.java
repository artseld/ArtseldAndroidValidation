package com.artseld.android.validation.validator;

import android.content.Context;
import com.artseld.android.validation.R;

public class IdenticalValidator extends AbstractValidator {

    private Object mToken;
    private boolean mStrict = true;

    public IdenticalValidator(Context context) {
        super(context);
        setMessage();
    }

    public boolean isValid(Object value) throws IllegalArgumentException {
        if (mToken != null) {
            if (mStrict) {
                return value.equals(mToken);
            }
            if (!(value instanceof String)) {
                throw new IllegalArgumentException("Can not to use non-strict format with non-strings");
            }
            String val = (String) ((CharSequence) value);
            String token = (String) ((CharSequence) mToken);
            return val.equalsIgnoreCase(token);
        }
        return true;
    }

    public void setToken(Object token) {
        mToken = token;
        setMessage();
    }

    public void setStrict() {
        setStrict(true);
    }

    public void setStrict(boolean isStrict) {
        mStrict = isStrict;
    }

    private void setMessage() {
        setMessage(mContext.getResources().getString(R.string.validation_identical)
            .replace("%TOKEN%", String.valueOf(mToken)));
    }

}
