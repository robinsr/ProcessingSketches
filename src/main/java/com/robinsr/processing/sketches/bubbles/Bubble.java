package com.robinsr.processing.sketches.bubbles;

import processing.core.PApplet;
import processing.core.PConstants;

/**
 * Models a single bubble.
 */
public class Bubble {
    private PApplet applet;

    private static final int MIN_BUBBLE_SIZE = 60;
    private static final int COLOR_ALPHA = 100;
    private static final int[] RGB_VALS = { 100, 150, 200, 255 };

    private float phase;
    private float deltaPhase;
    private int speed;
    private int initialSize;

    private final int[] colors;
    private final int[] coords;
    private int size;

    /**
     * Create a new bubble
     * @param parent Processing applet container
     * @param order integer used as seed for perlin noise
     */
    Bubble(PApplet parent, int order) {
        applet = parent;

        // Use perlin noise to create a wave-like effect. New bubbles are
        // created with similar size, position, and speed to the previous,
        // instead of totally at random
        float scale = applet.noise(order);

        phase = applet.map(scale, 0, 1, 0.0f, PConstants.TWO_PI);
        deltaPhase = applet.map(scale, 0, 1, .02f, .06f);
        initialSize = (int) applet.map(scale, 0, 1, MIN_BUBBLE_SIZE, applet.width/1.33f);
        size = initialSize;

        coords = new int[]{
            (int) applet.map(scale, 0, 1, 0, applet.width), // X coord
            (int) applet.map(scale, 0, 1, 0, applet.height) + applet.height // y coord
        };

        // Using a fixed set of potential values for the color channels
        // reduces randomness amongst the colors, while still feeling dynamic
        colors = new int[] {
            getColor(), getColor(), getColor()
        };
    }

    /**
     * Processing's coordinate drawing system is top->bottom, left->right. A Y-value
     * of 0 indicates that the center of the bubble is at the top of the screen.
     * If the Y-value is negative then the bubble's center is above the screen.
     * When the Y-value is so negative that none of the bubble is visible, it
     * can be safely ignored, and garbaged-collected
     * @return boolean, is bubble on screen
     */
    public boolean isOnScreen() {
        return coords[1] > -size / 2;
    }

    /**
     * Determines new coordinates and speed values
     */
    public void animate() {
        if (!isOnScreen()) {
            return;
        }

        // iterate the phase
        phase += deltaPhase;

        // calculate new size, speed, and coordinates
        size = (int) ((PApplet.sin(phase) * initialSize/4) + initialSize/2) + MIN_BUBBLE_SIZE;
        speed = applet.height / size;
        coords[1] = coords[1] - speed;
    }

    /**
     * Draws itself to the screen using applet interface
     */
    public void draw() {
        applet.fill(colors[0], colors[1], colors[2], COLOR_ALPHA);
        applet.ellipse(coords[0], coords[1], size, size);
    }

    /**
     * Returns a value at random for potential RGB values
     * @return integer color channel value
     */
    private int getColor() {
        return RGB_VALS[(int) applet.random(RGB_VALS.length)];
    }
}
