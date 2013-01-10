package com.artseld.android.validation.validator;

import android.content.Context;
import com.artseld.android.validation.R;

public class FloatValidator extends NumberValidator {

    public FloatValidator(Context context) {
        super(context);
        setMessage(R.string.validation_integer);
        setType(NumberValidator.FLOAT);
    }

}
