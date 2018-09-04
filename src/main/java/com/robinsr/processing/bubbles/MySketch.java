package com.robinsr.processing.bubbles;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.robinsr.processing.bubbles.Utils.getColor;

public class MySketch extends PApplet{

    public static final int MAX_NUMBER_OF_BUBBLES = 30;
    public static final int INITIAL_NUMBER_OF_BUBBLES = 4;

    private List<Bubble> bubblesInFrame;
    private int[] bg;
    private boolean paused = false;
    private int bubbleNo = 0;

    public void settings() {
        //size(720, 480); // raspi setting for testing
        fullScreen();
        //noLoop();
        //colorMode(HSB, 255);
        pixelDensity(2);

    }

    public void setup(){
        frameRate(30);
        noStroke();

        bg = new int[] {
            getColor(this), getColor(this), getColor(this)
        };

        bubblesInFrame = generateBubbles(INITIAL_NUMBER_OF_BUBBLES);
    }

    public void draw(){
        if (paused) {
            return;
        }

        //background(bg[0],bg[1],bg[2]);
        background(0);

        bubblesInFrame = bubblesInFrame.stream().filter(b -> {
            fill(b.colors[0], b.colors[1], b.colors[2], 100);
            ellipse(b.coords[0], b.coords[1], b.size, b.size);
            return b.animate();
        }).collect(Collectors.toList());

        bubblesInFrame.addAll(generateBubbles(MAX_NUMBER_OF_BUBBLES - bubblesInFrame.size()));
    }

    private List<Bubble> generateBubbles(int no) {
        List<Bubble> bubbles = new ArrayList();

        for(int i = 0; i < no; i++){
            bubbles.add(new Bubble(this, bubbleNo));
            bubbleNo++;
        }

        return bubbles;
    }

    public void mouseClicked() {
        paused = !paused;
    }

    public static void main(String[] passedArgs) {
        for (String s : passedArgs) {
            System.out.println(s);
        }
        String[] appletArgs = new String[] { "com.robinsr.processing.bubbles.MySketch" };
        PApplet.main(appletArgs);
    }
}
