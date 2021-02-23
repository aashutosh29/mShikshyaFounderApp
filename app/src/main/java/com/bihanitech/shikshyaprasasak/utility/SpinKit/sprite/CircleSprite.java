package com.bihanitech.shikshyaprasasak.utility.SpinKit.sprite;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Rajesh.
 */
public class CircleSprite extends ShapeSprite {

    @Override
    public ValueAnimator onCreateAnimation() {
        return null;
    }

    @Override
    public void drawShape(Canvas canvas, Paint paint) {
        if (getDrawBounds() != null) {
            int radius = Math.min(getDrawBounds().width(), getDrawBounds().height()) / 2;
            canvas.drawCircle(getDrawBounds().centerX(),
                    getDrawBounds().centerY(),
                    radius, paint);
        }
    }
}
