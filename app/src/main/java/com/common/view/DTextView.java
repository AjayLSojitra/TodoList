package com.common.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class DTextView extends AppCompatTextView {
    public DTextView(Context context) {
        super(context);
    }

    public DTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TextViewHelper.setTypeface(context, this, attrs);
    }
}
