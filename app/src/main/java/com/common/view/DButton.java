package com.common.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class DButton extends AppCompatButton {

    public DButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TextViewHelper.setTypeface(context, this, attrs);
    }
}
