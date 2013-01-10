package com.artseld.android.validation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.artseld.android.validation.validator.AbstractValidator;
import com.artseld.android.validation.validator.NotEmptyValidator;

import java.util.ArrayList;
import java.util.HashMap;

public class Validation {

    private Context mContext;

    private HashMap<View, String> viewsNames;
    private HashMap<View, Boolean> viewsRequired;
    private HashMap<View, ArrayList<AbstractValidator>> viewsValidators;
    private HashMap<View, ArrayList<String>> viewsErrors;

    private static final int TYPE_EDITTEXT      = 1;
    private static final int TYPE_TEXTVIEW      = 2;
    private static final int TYPE_DATEPICKER    = 3;

    private HashMap<String, Integer> supportedViews;

    /**
     * Initialization
     */

    public Validation(Context context) {
        mContext = context;

        viewsNames = new HashMap<View, String>();
        viewsRequired = new HashMap<View, Boolean>();
        viewsValidators = new HashMap<View, ArrayList<AbstractValidator>>();
        viewsErrors = new HashMap<View, ArrayList<String>>();

        supportedViews = new HashMap<String, Integer>();
        supportedViews.put("EditText", TYPE_EDITTEXT);
        supportedViews.put("TextView", TYPE_TEXTVIEW);
        supportedViews.put("DatePicker", TYPE_DATEPICKER);
    }

    /**
     * Setting Items and Validation Rules
     */

    public Validation addView(View v) {
        return addView(v, null, false);
    }

    public Validation addView(View v, boolean isRequired) {
        return addView(v, null, isRequired);
    }

    public Validation addView(View v, String name) {
        return addView(v, name, false);
    }

    public Validation addView(View v, String name, boolean isRequired) throws IllegalArgumentException {
        if (!supportedViews.containsKey(v.getClass().getSimpleName())) {
            throw new IllegalArgumentException("View type is unsupported");
        }
        if (viewsNames.containsKey(v)) {
            throw new IllegalArgumentException("View is already present in list");
        }
        viewsNames.put(v, (name != null) ? name : String.valueOf(v.getId()));
        viewsRequired.put(v, isRequired);
        viewsValidators.put(v, new ArrayList<AbstractValidator>());
        if (isRequired) {
            viewsValidators.get(v).add(new NotEmptyValidator(mContext));
        }
        viewsErrors.put(v, new ArrayList<String>());
        return this;
    }

    public Validation removeView(View v) {
        checkViewExistence(v);
        viewsNames.remove(v);
        viewsRequired.remove(v);
        viewsValidators.remove(v);
        viewsErrors.remove(v);
        return this;
    }

    public Validation removeViews() {
        viewsNames.clear();
        viewsRequired.clear();
        viewsValidators.clear();
        viewsErrors.clear();
        return this;
    }

    public Validation setRequired(View v) {
        return setRequired(v, true, 0);
    }

    public Validation setRequired(View v, boolean isRequired) {
        return setRequired(v, isRequired, 0);
    }

    public Validation setRequired(View v, int message) {
        return setRequired(v, true, message);
    }

    public Validation setRequired(View v, boolean isRequired, int message) {
        checkViewExistence(v);
        viewsRequired.put(v, isRequired);
        if (isRequired && !hasNotEmptyValidator(v)) {
            AbstractValidator val = new NotEmptyValidator(mContext);
            if (message != 0) {
                val.setMessage(message);
            }
            viewsValidators.get(v).add(val);
        }
        return this;
    }

    public boolean isRequired(View v) {
        checkViewExistence(v);
        return viewsRequired.get(v);
    }

    public Validation addValidator(View v, AbstractValidator validator) {
        checkViewExistence(v);
        viewsValidators.get(v).add(validator);
        if (validator instanceof NotEmptyValidator) {
            viewsRequired.put(v, true);
        }
        return this;
    }

    public boolean hasValidator(View v, AbstractValidator validator) {
        checkViewExistence(v);
        return viewsValidators.get(v).contains(validator);
    }

    private boolean hasNotEmptyValidator(View v) {
        checkViewExistence(v);
        boolean result = false;
        for (AbstractValidator val : viewsValidators.get(v)) {
            if (val instanceof NotEmptyValidator) {
                result = true;
                break;
            };
        }
        return result;
    }

    public Validation removeValidator(View v, AbstractValidator validator) {
        checkViewExistence(v);
        viewsValidators.get(v).remove(validator);
        if (validator instanceof NotEmptyValidator) {
            if (!hasNotEmptyValidator(v)) {
                viewsRequired.put(v, false);
            }
        }
        return this;
    }

    private void checkViewExistence(View v) throws IllegalArgumentException {
        if (!viewsNames.containsKey(v)) {
            throw new IllegalArgumentException("Trying to use a nonexistent view");
        }
    }

    public Validation clearValidators() {
        for (View v : viewsValidators.keySet()) {
            viewsValidators.get(v).clear();
            viewsErrors.get(v).clear();
        }
        return this;
    }

    public Validation clearErrors() {
        for (View v : viewsErrors.keySet()) {
            viewsErrors.get(v).clear();
        }
        return this;
    }

    /**
     * Validation
     */

    public boolean validate() {
        boolean result = true;
        if (viewsNames.size() > 0) {
            clearErrors();
            for (View v : viewsValidators.keySet()) {
                String value = extractValue(v);
                if (!viewsRequired.get(v) && (value == null || value.length() == 0)) continue;
                ArrayList<String> errors = viewsErrors.get(v);
                for (AbstractValidator val : viewsValidators.get(v)) {
                    boolean valResult = val.isValid(value);
                    errors.add(valResult ? null : val.getMessage());
                    if (result == true && valResult == false) {
                        result = false;
                    }
                }
            }
        }
        return result;
    }

    private String extractValue(View v) throws IllegalArgumentException {
        switch (supportedViews.get(v.getClass().getSimpleName())) {
            case TYPE_DATEPICKER:
                return String.valueOf(((DatePicker) v).getYear()) + "-"
                    + String.valueOf(((DatePicker) v).getMonth()) + "-"
                    + String.valueOf(((DatePicker) v).getDayOfMonth());
            case TYPE_TEXTVIEW:
            case TYPE_EDITTEXT:
                return ((EditText) v).getText().toString();
            default:
                throw new IllegalArgumentException("Trying to extract value from field with unsupported type");
        }
    }

    /**
     * Output and Dialogs
     */

    public ArrayList<AbstractValidator> getValidators(View v) {
        checkViewExistence(v);
        return viewsValidators.get(v);
    }

    public ArrayList<String> getErrors(View v) {
        checkViewExistence(v);
        return viewsErrors.get(v);
    }

    public void showErrorsDialog(Activity activity) {
        AlertDialog alertDialog;

        Context context = activity.getApplicationContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.validation_dialog, (ViewGroup) activity.findViewById(R.id.validation_root));

        AlertDialog.Builder adb = new AlertDialog.Builder(activity);
        adb.setView(layout);
        adb.setTitle(R.string.validation_dialog_title);

        ListView listView = (ListView) layout.findViewById(R.id.validation_list);
        ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

        HashMap<String, String> map;
        for (View v : viewsErrors.keySet()) {
            boolean isFirstViewIteration = true;
            for (String str : viewsErrors.get(v)) {
                if (str == null) continue;
                map = new HashMap<String, String>();
                if (isFirstViewIteration) {
                    map.put("name", viewsNames.get(v));
                    isFirstViewIteration = false;
                } else {
                    map.put("name", "");
                }
                map.put("error", str);
                listItem.add(map);
            }
        }

        SimpleAdapter mSchedule = new SimpleAdapter(layout.getContext(), listItem, R.layout.validation_dialog_list_item,
            new String[] {"name", "error"}, new int[] {R.id.validation_item_name, R.id.validation_item_error}) {
            @Override
            public boolean isEnabled(int position) {
                return false;
            }
        };

        listView.setAdapter(mSchedule);
        listView.setSelector(android.R.color.transparent);

        adb.setPositiveButton(R.string.validation_dialog_close, null);

        alertDialog = adb.create();
        alertDialog.show();
    }

}
