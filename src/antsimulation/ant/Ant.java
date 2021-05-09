package antsimulation.ant;

import antsimulation.Main;
import antsimulation.world.Displayable;
import antsimulation.world.Updatable;
import processing.core.PVector;

public class Ant implements Updatable, Displayable {

    private static final float TURN_AMOUNT = 20f;
    private static final float BODY_RADIUS = 2f;

    private final float movementSpeed = 35f;

    private final PVector pos;
    private final PVector movement = PVector.random2D().setMag(movementSpeed / Main.getApp().frameRate);

    public Ant(float x, float y) {
        this.pos = new PVector(x, y);
    }

    public void update() {
        turnRandomly();
        move();
    }

    public void display() {
        Main.getApp().noStroke();
        Main.getApp().fill(5, 5, 5);
        Main.getApp().circle(pos.x, pos.y, 2 * BODY_RADIUS);
    }

    private void turnRandomly() {
        movement.rotate(Main.getApp().random(-TURN_AMOUNT / Main.getApp().frameRate, TURN_AMOUNT / Main.getApp().frameRate));
    }

    private void move() {
        PVector attemptedPos = this.pos.copy().add(movement);

        if (Main.getWorld().inBounds(attemptedPos)) pos.add(movement);
        else pos.add(movement.rotate(180));
    }
}
