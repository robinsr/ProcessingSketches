package com.robinsr.processing.sketches.bubbles;

import com.robinsr.processing.sketches.BaseSketch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Bubbles is a simple sketch program that draws a series of circles to
 * the screen. Each circular bubble "floats" up with variable speed
 * determined by its size. The result is a kind of lava lamp-like effect.
 */
public class Bubbles extends BaseSketch {

    public static final int MAX_NUMBER_OF_BUBBLES = 30;
    public static final int INITIAL_NUMBER_OF_BUBBLES = 4;

    private List<Bubble> bubblesInFrame;
    private boolean paused = false;
    private int bubbleNo = 0;

    public void setup(){
        frameRate(30);
        noStroke();

        bubblesInFrame = generateNewBubbles(INITIAL_NUMBER_OF_BUBBLES);
    }

    public void draw() {
        if (paused) {
            return;
        }

        background(0);

        bubblesInFrame = bubblesInFrame.stream()
                .filter(Bubble::isOnScreen)
                .peek(Bubble::animate)
                .peek(Bubble::draw)
                .collect(Collectors.toList());

        bubblesInFrame.addAll(generateNewBubbles(MAX_NUMBER_OF_BUBBLES - bubblesInFrame.size()));
    }

    /**
     * Creates a List of new bubble objects
     * @param no desired size of the list size of the list
     * @return List of newly created bubble objects
     */
    private List<Bubble> generateNewBubbles(int no) {
        List<Bubble> bubbles = new ArrayList();

        for (int i = 0; i < no; i++) {

            // Prevent integer wrap-around
            if (bubbleNo == MAX_INT) {
                bubbleNo = 0;
            }

            bubbles.add(new Bubble(this, bubbleNo));
            bubbleNo++;
        }

        return bubbles;
    }

    public void mouseClicked() {
        paused = !paused;
    }


    @Override
    public String getName() {
        return "Bubbles";
    }
}
