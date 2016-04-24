package com.pepela.sunshine.sunshine.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pepela.sunshine.sunshine.R;

public class LocationEditTextPreference extends EditTextPreference {
    private static final int DEFAULT_MINIMUM_LOCATION_LENGTH = 3;
    private static final int DEFAULT_MAXIMUM_LOCATION_LENGTH = 30;
    private int mMinLength;
    private int mMaxLength;


    public LocationEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.LocationEditTextPreference,
                0,
                0);
        try {
            mMinLength = a.getInteger(R.styleable.LocationEditTextPreference_minLength, DEFAULT_MINIMUM_LOCATION_LENGTH);
            mMaxLength = a.getInteger(R.styleable.LocationEditTextPreference_maxLength, DEFAULT_MAXIMUM_LOCATION_LENGTH);
        } catch (Exception e) {
            a.recycle();
        }
    }

    @Override
    protected void showDialog(Bundle state) {
        super.showDialog(state);


        EditText editText = getEditText();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Dialog dialog = getDialog();
                if (dialog instanceof AlertDialog) {
                    AlertDialog alertDialog = (AlertDialog) dialog;
                    Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    if (s.length() >= mMinLength && s.length() <= mMaxLength) {
                        positiveButton.setEnabled(true);
                    } else {
                        positiveButton.setEnabled(false);
                    }
                }
            }
        });

    }

}
