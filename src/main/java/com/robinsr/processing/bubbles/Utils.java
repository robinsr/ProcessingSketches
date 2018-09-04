package com.robinsr.processing.bubbles;

import processing.core.PApplet;


public class Utils {
    public static int getColor(PApplet p) {
        int[] rgbVals = {100, 150, 200, 255};
        return rgbVals[(int)p.random(rgbVals.length)];
        //return (int) p.random(0, 255);
    }
}
