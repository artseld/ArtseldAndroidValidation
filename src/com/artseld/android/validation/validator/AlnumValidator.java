package com.artseld.android.validation.validator;

import android.content.Context;
import com.artseld.android.validation.R;

public class AlnumValidator extends RegExpValidator {

    public AlnumValidator(Context context) {
        super(context);
        setMessage(R.string.validation_alnum);
        setPattern("^[A-Za-z0-9]+$");
    }

}
