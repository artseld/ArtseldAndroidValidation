package com.artseld.android.validation.validator;

import android.content.Context;
import android.webkit.URLUtil;
import com.artseld.android.validation.R;

public class UrlValidator extends AbstractValidator {

    public UrlValidator(Context context) {
        super(context, R.string.validation_url);
    }

    public boolean isValid(Object value) {
        if (!(new NotEmptyValidator(mContext).isValid(value))) {
            return true;
        }
        String val = (String) ((CharSequence) value);
        return URLUtil.isValidUrl(val);
    }

}
