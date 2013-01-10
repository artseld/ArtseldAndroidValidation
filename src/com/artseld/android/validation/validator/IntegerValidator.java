package com.artseld.android.validation.validator;

import android.content.Context;
import com.artseld.android.validation.R;

public class IntegerValidator extends NumberValidator {

    public IntegerValidator(Context context) {
        super(context);
        setMessage(R.string.validation_integer);
        setType(NumberValidator.INTEGER);
    }

}
