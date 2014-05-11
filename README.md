ArtseldAndroidValidation
========================

Validation library to use in Android applications

About
-----

Library code based on Zend_Validator, part of popular and powerful PHP Framework [Zend Framework](http://framework.zend.com).
Validation rules can be applied to several types of views: EditText, TextView and DatePicker.

### License

* [Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

How to use
----------

Library consists of 2 parts:
-   **Validation :** Main class for adding views, validators, editing, handling, removing, getting errors, etc. By other words, Validatiob object is a group of views with validators attached to them.
-   **Validator(s) :** Parent abstract class and validators suite with different rules for special situations.

### Validator

Current implementation contains 6 main validators (13 total):
+   **NotEmptyValidator** : Checks that the value is not empty (you can use method setRequired() of Validation object instead this Validator).
+   **IdenticalValidator** : Checks that the value is equals to setted Token (can be strict or not stict).
+   **UrlValidator** : Checks that the value contains a valid URL address.
+   **DateValidator** : Checks that the value contains a valid date (not nice implementation, just test it and propose decisions for improvement).
+   **NumberValidator** : Checks that the value contains a number (based-on validators: IntegerValidator, LongValidator, FloatValidator, DoubleValidator).
+   **RegExpValidator** : Checks that the value is matches setted Pattern (based-on validators: AllnumValidator, EmailValidator, HexValidator).

You can create your custom validator, of course. You should create class and extend it from AbstractValidator class:

``` java
public class MyValidator extends AbstractValidator
{
    public MyValidator(Context context) {
        super(context, R.string.validation_my_message);
    }

    public boolean isValid(Object value) {
        // Your validation code (TRUE of FALSE)
        // Return TRUE if ok, FALSE if validation failed
        return true;
    }
}
```

It's all! You can extend existing validators such as RegExpValidator or NumberValidator, of course.
    
### Validation

First, you should instanciate Validation object (validation group):

``` java
Validation myGroup;
...
myGroup = new Validation(context);
```

For example, you have 3 fields - Name, Email, Age. And button Save:

``` java
EditText name;
EditText email;
TextView age;
Button save;
...
name = (EditText) findViewById(R.id.edit_name);
email = (EditText) findViewById(R.id.edit_email);
age = (TextView) findViewById(R.id.text_age);
save = (Button) findViewById(R.id.button_save);
```

After, add fields and validators:

``` java
myGroup
  .addView(name, getResources().getString(R.string.edit_name))
  .setRequired();
myGroup
  .addView(email, getResources().getString(R.string.edit_email))
  .addValidator(email, new EmailValidator(context));

IntegerValidator integerValidator = new IntegerValidator(context);
integerValidator.setMessage("It is incorrect age!");

myGroup
  .addView(age, getResources().getString(R.string.text_age))
  .addValidator(age, new NotEmptyValidator(context))
  .addValidator(age, integerValidator);
```

What does this code means? We marked view Name as required and validator NotEmptyValidator was attached automatically to it.
We attached EmailValidator to the second view, Email, but this view was not marked as required and validator will check not empty value only.
We decided that default error message of IntegerValidator is not nice in current situtation and set custom message.
And we added NotEmptyValidator (it is the same as setting view as required, you remember).
Ok! Now, the last step.
Add click listener and validation checker to our Save button (for example, you going to write this code in your MyActivity class):

``` java
save.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (!myGroup.validate()) {
            // Validation failed! We can show nice errors alert dialog
            myGroup.showErrorsDialog(MyActivity.this);
        } else {
            // Validation passed successfully! Do something
        }
    }
}
```

It is not completed documentation. See the code and find more usefult features and possibilities.
Enjoy! And let's do this library better together =)

### Changelog

+   **0.0.1** : First implementation
