/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tests.shapes;

import gamelib.canvas.Drawable;
import gamelib.shape.GameShape;

import java.awt.*;

/**
 *
 * @author angus thomsen <aksthomsen@gmail.com>
 */
public class SimpleShape extends GameShape implements Drawable {

    /**
     * extends Shape, and by default it takes the form of a square when drawn on 
     * screen. It's primary purpose is to provide a shape for debuging.
     */
    public SimpleShape(int x, int y, int width, int height) {
        centerPoints(x, y);
        setSize(width, height);

        velocity = 0;
        acceleration = 0.1;
        rotationVelocity = 5;
        projectory = 180.00;
    }

    @Override
    public void draw(Graphics g) {
        g.fillRect((int) point.getX(), (int) point.getY(), width, height);
    }
}
