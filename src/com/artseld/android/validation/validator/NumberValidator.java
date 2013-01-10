package com.artseld.android.validation.validator;

import android.content.Context;
import com.artseld.android.validation.R;

public class NumberValidator extends AbstractValidator {

    protected static final int INTEGER  = 1;
    protected static final int LONG     = 2;
    protected static final int FLOAT    = 3;
    protected static final int DOUBLE   = 4;

    private int mType = 0;

    public NumberValidator(Context context) {
        super(context);
        setMessage(R.string.validation_number);
    }

    public boolean isValid(Object value) {
        if (!(new NotEmptyValidator(mContext).isValid(value))) {
            return true;
        }
        if (mType == 0) {
            return true;
        }
        String val = (String) ((CharSequence) value);
        try {
            checkValue(val);
        } catch(NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public void setType(int type) {
        mType = type;
    }

    private void checkValue(String value) throws NumberFormatException, NullPointerException {
        switch (mType) {
            case INTEGER:
                Integer.parseInt(value);
                break;
            case LONG:
                Long.parseLong(value);
                break;
            case FLOAT:
                Float.parseFloat(value);
                break;
            case DOUBLE:
                Double.parseDouble(value);
                break;
        }
    }

}
