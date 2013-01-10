package com.artseld.android.validation.validator;

import android.content.Context;
import com.artseld.android.validation.R;

import java.util.regex.Pattern;

public class RegExpValidator extends AbstractValidator {

    private static Pattern mPattern;

    public RegExpValidator(Context context) {
        super(context, R.string.validation_regexp);
    }

    public boolean isValid(Object value) {
        if (!(new NotEmptyValidator(mContext).isValid(value))) {
            return true;
        }
        if (mPattern == null) {
            return true;
        }
        String val = (String) ((CharSequence) value);
        return mPattern.matcher(val).matches();
    }

    public RegExpValidator setPattern(String pattern) {
        mPattern = Pattern.compile(pattern);
        return this;
    }

}
