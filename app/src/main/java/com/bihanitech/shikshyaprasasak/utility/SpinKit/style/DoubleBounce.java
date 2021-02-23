package com.bihanitech.shikshyaprasasak.utility.SpinKit.style;

import android.animation.ValueAnimator;
import android.os.Build;

import com.bihanitech.shikshyaprasasak.utility.SpinKit.animation.SpriteAnimatorBuilder;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.sprite.CircleSprite;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.sprite.Sprite;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.sprite.SpriteContainer;


/**
 * Created by rajesh.
 */
public class DoubleBounce extends SpriteContainer {

    @Override
    public Sprite[] onCreateChild() {
        return new Sprite[]{
                new Bounce(), new Bounce()
        };
    }

    @Override
    public void onChildCreated(Sprite... sprites) {
        super.onChildCreated(sprites);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sprites[1].setAnimationDelay(1000);
        } else {
            sprites[1].setAnimationDelay(-1000);
        }
    }

    private class Bounce extends CircleSprite {

        Bounce() {
            setAlpha(153);
            setScale(0f);
        }

        @Override
        public ValueAnimator onCreateAnimation() {
            float[] fractions = new float[]{0f, 0.5f, 1f};
            return new SpriteAnimatorBuilder(this).scale(fractions, 0f, 1f, 0f).
                    duration(2000).
                    easeInOut(fractions)
                    .build();
        }
    }
}
