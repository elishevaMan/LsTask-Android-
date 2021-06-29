package com.example.lsapplication.helper.validators;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.lsapplication.R;
import com.google.android.material.textfield.TextInputLayout;

public class ValidatingTextInputLayout extends TextInputLayout {
    private static final int EMAIL_VALIDATOR = 1;
    private static final int PHONE_VALIDATOR = 2;
    private static final int FULLNAME_VALIDATOR = 3;
    private static final int PASSWORD = 4;
    private static final int REPEAT_PASSWORD = 5;
    private static final int PASSWORD_OR_EMPTY = 6;
    private static final int NOT_NULL = 7;
    private static final int ID_VALIDATOR = 8;
    public Validator getValidator() {
        return validator;
    }

    private Validator validator;
    private CharSequence errorLabel;
    private CharSequence match;

    public ValidatingTextInputLayout(Context context) {
        this(context, null);
    }

    public ValidatingTextInputLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ValidatingTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ValidatingTextInputLayout);
        setBoxBackgroundColor(Color.TRANSPARENT);
//        setErrorTextColor(Color.RED);
        errorLabel = a.getString(R.styleable.ValidatingTextInputLayout_errorLabel);

        int validatorValue = a.getInt(R.styleable.ValidatingTextInputLayout_validator, 0);
        switch (validatorValue) {
            case EMAIL_VALIDATOR:
                validator = Validators.EMAIL;
                break;
            case PHONE_VALIDATOR:
                validator = Validators.PHONE;
                break;
            case ID_VALIDATOR:
                validator = Validators.ID;
                break;
            case FULLNAME_VALIDATOR:
                validator = Validators.FULLNAME;
                break;
            case PASSWORD:
                validator = Validators.PASSWORD;
                break;
            case REPEAT_PASSWORD:
                validator = Validators.REPEAT_PASSWORD;
                match = a.getString(R.styleable.ValidatingTextInputLayout_paswwordMatch);
                break;
            case PASSWORD_OR_EMPTY:
                validator = Validators.PASSWORD_OR_EMPTY;
                break;
            case NOT_NULL:
                validator = Validators.NOT_NULL;
                break;
        }

        a.recycle();

    }

    /**
     * Set a {@link Validator} for validating the contained {@code EditText} input text.
     */
    public void setValidator(@NonNull Validator validator) {
        this.validator = validator;
    }

    /**
     * Set the label to show when {@link #validate()} returns {@literal false}.
     */
    public void setErrorLabel(CharSequence label) {
        errorLabel = label;
    }

    public void setPaswwordMatch(CharSequence string) {
        match = string;
    }

    /**
     * Invoke this when you want to validate the contained {@code EditText} input text against the
     * provided {@link Validator}. For validating multiple {@code ValidatingTextInputLayout}
     * objects at once, call {@link Validators#validate(ValidatingTextInputLayout...)}. Throws an
     * {@code IllegalStateException} if either no helper.validator has been set or an error is triggered
     * and no error label is set.
     */
    public boolean validate() {

        if (validator == null) {
            throw new IllegalStateException("A Validator must be set; call setValidator first.");
        }

        CharSequence input = "";
        EditText editText = getEditText();
        if (editText != null) {
            input = editText.getText();
        }

        boolean valid = match != null ? validator.isValid(input.toString(), match.toString()) : validator.isValid(input.toString());
        if (valid) {
            setError(null);
        } else {
            if (errorLabel == null) {
                throw new IllegalStateException("An error label must be set when validating an " +
                        "invalid input; call setErrorLabel or app:errorLabel first.");
            }
            setError(errorLabel);
        }

        return valid;
    }

    public boolean validate(String match) {
        if (validator == null) {
            throw new IllegalStateException("A Validator must be set; call setValidator first.");
        }

        CharSequence input = "";
        EditText editText = getEditText();
        if (editText != null) {
            input = editText.getText();
        }

        boolean valid = match != null ? validator.isValid(input.toString(), match.toString()) : validator.isValid(input.toString());
        if (valid) {
            setError(null);
        } else {
            if (errorLabel == null) {
                throw new IllegalStateException("An error label must be set when validating an " +
                        "invalid input; call setErrorLabel or app:errorLabel first.");
            }
            requestFocus();
            setError(errorLabel);
        }

        return valid;
    }

    public void emptyError() {
        /*if (getError() != null)*/
        setError("  ");
    }

}


