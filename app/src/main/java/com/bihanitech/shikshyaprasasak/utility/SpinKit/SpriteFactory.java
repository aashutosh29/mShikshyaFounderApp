package com.bihanitech.shikshyaprasasak.utility.SpinKit;


import com.bihanitech.shikshyaprasasak.utility.SpinKit.sprite.Sprite;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.style.ChasingDots;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.style.Circle;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.style.CubeGrid;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.style.DoubleBounce;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.style.FadingCircle;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.style.FoldingCube;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.style.MultiplePulse;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.style.MultiplePulseRing;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.style.Pulse;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.style.PulseRing;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.style.RotatingCircle;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.style.RotatingPlane;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.style.ThreeBounce;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.style.WanderingCubes;
import com.bihanitech.shikshyaprasasak.utility.SpinKit.style.Wave;

/**
 * Created by Aashutosh.
 */
public class SpriteFactory {

    public static Sprite create(Style style) {
        Sprite sprite = null;
        switch (style) {
            case ROTATING_PLANE:
                sprite = new RotatingPlane();
                break;
            case DOUBLE_BOUNCE:
                sprite = new DoubleBounce();
                break;
            case WAVE:
                sprite = new Wave();
                break;
            case WANDERING_CUBES:
                sprite = new WanderingCubes();
                break;
            case PULSE:
                sprite = new Pulse();
                break;
            case CHASING_DOTS:
                sprite = new ChasingDots();
                break;
            case THREE_BOUNCE:
                sprite = new ThreeBounce();
                break;
            case CIRCLE:
                sprite = new Circle();
                break;
            case CUBE_GRID:
                sprite = new CubeGrid();
                break;
            case FADING_CIRCLE:
                sprite = new FadingCircle();
                break;
            case FOLDING_CUBE:
                sprite = new FoldingCube();
                break;
            case ROTATING_CIRCLE:
                sprite = new RotatingCircle();
                break;
            case MULTIPLE_PULSE:
                sprite = new MultiplePulse();
                break;
            case PULSE_RING:
                sprite = new PulseRing();
                break;
            case MULTIPLE_PULSE_RING:
                sprite = new MultiplePulseRing();
                break;
            default:
                break;
        }
        return sprite;
    }
}
