package com.talentica.bookshelf.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.talentica.bookshelf.R;

/**
 * Created by rohanr on 7/14/16.
 */
public class CustomButton extends Button{
    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        //Typeface.createFromAsset doesn't work in the layout editor. Skipping...
        if (isInEditMode()) {
            return;
        }

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.CustomTextStyle);
        String fontName = styledAttrs.getString(R.styleable.CustomTextStyle_typeface);
        styledAttrs.recycle();

        if (fontName != null) {
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontName);
            setTypeface(typeface);
        }

        setTransformationMethod(null);
    }
}
