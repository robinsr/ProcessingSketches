package com.robinsr.processing.sketches;


import processing.core.PApplet;


/**
 * This is a base class for which all sketches in this project extend.
 * They can then all be found with reflection
 */
public abstract class BaseSketch extends PApplet {

    public void settings() {
        size(720, 480); // raspi setting for testing
//        fullScreen();
//        noLoop();
//        colorMode(HSB, 255);
        try {
            pixelDensity(2);
        } catch (RuntimeException e) {
            pixelDensity(1);
        }
    }

    public void runSketch(String[] passedArgs) {
        PApplet.main(passedArgs);
    }

    public abstract String getSketchName();
}