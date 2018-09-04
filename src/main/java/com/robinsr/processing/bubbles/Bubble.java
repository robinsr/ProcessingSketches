package com.robinsr.processing.bubbles;

import processing.core.PApplet;
import processing.core.PConstants;

import static com.robinsr.processing.bubbles.Utils.getColor;


public class Bubble {
    PApplet p;

    private static int MIN_BUBBLE_SIZE = 60;

    private float phase;
    private float dPhase;
    private int speed;
    private int initialSize;


    public int[] colors;
    public int[] coords;
    public int size;


    Bubble(PApplet parent, int order) {
        p = parent;

        // Use perlin noise to smooth out the initial size, phase, and position.
        float scale = p.noise(order);

        phase = p.map(scale, 0, 1, 0.0f, PConstants.TWO_PI);
        dPhase = p.map(scale, 0, 1, .02f, .06f);
        initialSize = (int) p.map(scale, 0, 1, MIN_BUBBLE_SIZE, p.width/1.33f);
        size = initialSize;

        coords = new int[]{
            (int)p.map(scale, 0, 1, 0, p.width), // X coord
            (int)p.map(scale, 0, 1, 0, p.height) + p.height // y coord
        };

        // Just use random colors for now
        colors = new int[] {
            getColor(p), getColor(p), getColor(p)
        };
    }

    private boolean isOnScreen() {
        return coords[1] > 0 - size/2;
    }

    public boolean animate() {
        if (!isOnScreen()) {
            return false;
        }

        // iterate the phase
        phase += dPhase;

        // calculate new size, speed, and coordinates
        size = (int) ((PApplet.sin(phase) * initialSize/4) + initialSize/2) + MIN_BUBBLE_SIZE;
        speed = p.height / size;
        coords[1] = coords[1] - speed;

        return true;
    }
}