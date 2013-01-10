package com.artseld.android.validation.validator;

import android.content.Context;
import com.artseld.android.validation.R;

public class DoubleValidator extends NumberValidator {

    public DoubleValidator(Context context) {
        super(context);
        setMessage(R.string.validation_double);
        setType(NumberValidator.DOUBLE);
    }

}
