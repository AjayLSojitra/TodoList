package com.common.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatRadioButton;

public class DRadioButton extends AppCompatRadioButton {

    public DRadioButton(Context context) {
        super(context);
    }

    public DRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TextViewHelper.setTypeface(context, this, attrs);
    }
}
