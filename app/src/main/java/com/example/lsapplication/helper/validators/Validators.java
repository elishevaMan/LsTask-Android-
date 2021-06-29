package com.example.lsapplication.helper.validators;

import android.util.Log;
import android.util.Patterns;

public final class Validators {
    /** Validates input for email formatting. */
    public static final Validator EMAIL;
    /** Validates input for phone number formatting. */
    public static final Validator PHONE;
    public static final Validator ID;
    /** Validates input for fullname formatting. */
    public static final Validator FULLNAME;
    /** Validates input for password formatting. */
    public static final Validator PASSWORD;
    /** Validates input for repeat password formatting. */
    public static final Validator REPEAT_PASSWORD;
    /** Validates input for  password formatting. */
    public static final Validator PASSWORD_OR_EMPTY;
    public static final Validator NOT_NULL;
    static {
        EMAIL = new Validator() {
            @Override
            public boolean isValid(String... input) {
                return Patterns.EMAIL_ADDRESS.matcher(input[0]).matches();
            }
        };

        PHONE = new Validator() {
            @Override
            public boolean isValid(String... input) {
                //  return Patterns.PHONE.matcher(input[0]).matches();
                return !input[0].isEmpty() && input[0].length()==10;
            }
        };
        ID = new Validator() {
            @Override
            public boolean isValid(String... input) {
                //  return Patterns.PHONE.matcher(input[0]).matches();
                return !input[0].isEmpty() && input[0].length()>=6 && input[0].length()<=9 ;
            }
        };
        FULLNAME = new Validator() {
            @Override
            public boolean isValid(String... input) {
                return !input[0].isEmpty() && input[0].contains(" ") && (input[0].split(" ")[0].length() > 1 && input[0].split(" ")[1].length() > 1);
//                input[0].isEmpty()||!input[0].contains(" ")?false:input[0].split(" ")[0].length()>1&&input[0].split(" ")[1].length()>1;
            }
        };
        PASSWORD = new Validator() {
            @Override
            public boolean isValid(String... input) {
                return    input[0].length()>5;

            }
        };
        REPEAT_PASSWORD = new Validator() {
            @Override
            public boolean isValid(String... input) {
                return    input[0].length()>5 &&input[0].equals(input[1]);

            }
        };
        PASSWORD_OR_EMPTY = new Validator() {
            @Override
            public boolean isValid(String... input) {
                return    input[0].length()>5 || input[0].length()==0;

            }
        };
        NOT_NULL = new Validator() {
            @Override
            public boolean isValid(String... input) {
                return    input[0].length()>0 ;

            }
        };
    }

    private Validators() {
        throw new AssertionError("No instances");
    }

    /** Validates multiple inputs at once and returns {@code true} if all inputs are valid. */
    public static boolean validate(ValidatingTextInputLayout... layouts) {
        boolean allInputsValid = true;

        for (ValidatingTextInputLayout layout : layouts) {
/*
            layout.setErrorEnabled(true);
*/
            if (layout.getValidator()==Validators.REPEAT_PASSWORD){
                ValidatingTextInputLayout password =null;
                for (ValidatingTextInputLayout layoutIn : layouts){
                    if (layoutIn.getValidator()==Validators.PASSWORD){
                        password =layoutIn;
                        break;
                    }
                }
                if (password!=null){
                    if (!layout.validate(password.getEditText().getText().toString())) {
                        allInputsValid = false;
                    }
                    continue;
                }
            }
            if (!layout.validate()) {
                allInputsValid = false;
            }
        }

        return allInputsValid;
    }
    /** Validates multiple inputs at once and returns {@code true} if all inputs are valid. */
    public static boolean validateWithoutEnabled(ValidatingTextInputLayout... layouts) {
        boolean allInputsValid = true;

        for (ValidatingTextInputLayout layout : layouts) {
            if (layout.getValidator()==Validators.REPEAT_PASSWORD){
                ValidatingTextInputLayout password =null;
                for (ValidatingTextInputLayout layoutIn : layouts){
                    if (layoutIn.getValidator()==Validators.PASSWORD){
                        password =layoutIn;
                        break;
                    }
                }
                if (password!=null){
                    if (!layout.validate(password.getEditText().getText().toString())) {
                        allInputsValid = false;
                    }
                    continue;
                }
            }
            if (!layout.validate()) {
                allInputsValid = false;
            }
        }

        return allInputsValid;
    }
    /**
     * Validates input for meeting a minimum number of characters. For validating against trimmed
     * input, {@link #minimum(int, boolean)}.
     */
    public static Validator minimum(int length) {
        return minimum(length, false);
    }

    /**
     * Validates input for meeting a minimum number of characters. Pass {@literal true} for
     * {@code trim} to validate against trimmed input.
     */
    public static Validator minimum(final int length, final boolean trim) {
        return new Validator() {
            @Override
            public boolean isValid(String... input) {
                if (trim) {
                    input[0] = input[0].trim();
                }

                return length <= input[0].length();
            }
        };
    }
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.w("tag", "onTextChanged " + s);
    }
}
