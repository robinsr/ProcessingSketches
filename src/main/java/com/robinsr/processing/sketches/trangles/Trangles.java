package com.robinsr.processing.sketches.trangles;

import com.robinsr.processing.sketches.BaseSketch;
import processing.core.PConstants;
import processing.core.PShape;

public class Trangles extends BaseSketch {

    @Override
    public String getSketchName() {
        return "Trangles";
    }

    private float r = 75;
    private float theta = 0;

    public void setup() {
        frameRate(120);
    }

    public void draw() {
        background(0);

        noStroke();
        fill(255);
        ellipse(width/2, height/2, 1, 1);

        int centerX = width/2;
        int centerY = height/2;

        Trangle trangle = new Trangle(centerX, centerY, 100);
        trangle.rotate(theta);
        shape(trangle, centerX, centerY);

        fill(255, 0, 0);
        ellipse(centerX, centerY, 1, 1);

        theta += PI/240;
    }

    private static class Trangle extends PShape {

        private int triangleSize = 100;
        private int curveSize = 30;

        public Trangle(int centerX, int centerY, int r) {
            setFamily(PShape.GEOMETRY);

            float theta = 0;

            float x1 = r * cos(theta);
            float y1 = r * sin(theta);
            theta += PI * 2/3;
            float x2 = r * cos(theta);
            float y2 = r * sin(theta);
            theta += PI * 2/3;
            float x3 = r * cos(theta);
            float y3 = r * sin(theta);

            beginShape();
            fill(255);
            vertex(x1, y1);
            vertex(x2, y2);
            vertex(x3, y3);
            endShape(PConstants.CLOSE);
        }
    }
}
