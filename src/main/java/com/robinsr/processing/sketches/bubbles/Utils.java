package com.robinsr.processing.sketches.bubbles;

import processing.core.PApplet;

/**
 * IDK, a utils class
 */
public class Utils {

    /**
     *
     * @param p
     * @return
     */
    public static int getColor(PApplet p) {
        int[] rgbVals = { 100, 150, 200, 255 };
        return rgbVals[(int) p.random(rgbVals.length)];
    }
}
