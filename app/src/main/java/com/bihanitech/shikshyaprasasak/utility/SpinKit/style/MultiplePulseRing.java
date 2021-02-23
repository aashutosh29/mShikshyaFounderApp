package com.bihanitech.shikshyaprasasak.utility.SpinKit.style;


import com.bihanitech.shikshyaprasasak.utility.SpinKit.sprite.Sprite;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.sprite.SpriteContainer;

/**
 * Created by Rajesh.
 */
public class MultiplePulseRing extends SpriteContainer {

    @Override
    public Sprite[] onCreateChild() {
        return new Sprite[]{
                new PulseRing(),
                new PulseRing(),
                new PulseRing(),
        };
    }

    @Override
    public void onChildCreated(Sprite... sprites) {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i].setAnimationDelay(200 * (i + 1));
        }
    }
}
