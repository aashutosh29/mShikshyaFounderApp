package com.bihanitech.shikshyaprasasak.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class CustomMaterialSpinner extends MaterialBetterSpinner {
    public CustomMaterialSpinner(Context context) {
        super(context);
    }
    public CustomMaterialSpinner(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
    }

    public CustomMaterialSpinner(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);

    }

    /**
     * Override to fix enable/disable problem.
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled())
            return super.onTouchEvent(event);
        else return false;
    }
}
