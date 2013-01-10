package com.artseld.android.validation.validator;

import android.content.Context;
import com.artseld.android.validation.R;

public class NotEmptyValidator extends AbstractValidator {

    public NotEmptyValidator(Context context) {
        super(context, R.string.validation_not_empty);
    }

    public boolean isValid(Object value) {
        String val = (String) ((CharSequence) value);
        if (val != null && val.length() > 0) {
            return true;
        }
        return false;
    }

}
