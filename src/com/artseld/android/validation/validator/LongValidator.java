package com.artseld.android.validation.validator;

import android.content.Context;
import com.artseld.android.validation.R;

public class LongValidator extends NumberValidator {

    public LongValidator(Context context) {
        super(context);
        setMessage(R.string.validation_long);
        setType(NumberValidator.LONG);
    }

}
