package com.artseld.android.validation.validator;

import android.content.Context;
import com.artseld.android.validation.R;

public class HexValidator extends RegExpValidator {

    public HexValidator(Context context) {
        super(context);
        setMessage(R.string.validation_hex);
        setPattern("^(#|)[0-9A-Fa-f]+$");
    }

}
