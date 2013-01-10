package com.artseld.android.validation.validator;

import android.content.Context;
import android.text.TextUtils;
import com.artseld.android.validation.R;

public class DateValidator extends AbstractValidator {

    public DateValidator(Context context) {
        super(context, R.string.validation_date);
    }

    public boolean isValid(Object value) {
        if (!(new NotEmptyValidator(mContext).isValid(value))) {
            return true;
        }
        String val = (String) ((CharSequence) value);
        String[] tokens = TextUtils.split(val, "-");
        IntegerValidator isInt = new IntegerValidator(mContext);
        if (tokens.length == 3 && isInt.isValid(tokens[0])
            && isInt.isValid(tokens[1]) && isInt.isValid(tokens[2])) {
            return true;
        }
        return false;
    }

}
