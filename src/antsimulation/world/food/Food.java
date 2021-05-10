package antsimulation.world.food;

import antsimulation.Main;
import antsimulation.world.Displayable;
import antsimulation.world.Locatable;
import processing.core.PVector;

public class Food implements Locatable, Displayable {

    private static final float RADIUS = 2f;

    private final PVector pos;

    public Food(PVector position) {
        this.pos = position;
    }

    @Override
    public void display() {
        Main.getApp().fill(20, 180, 20);
        Main.getApp().circle(pos.x, pos.y, 2 * RADIUS);
    }

    @Override
    public PVector getLocation() {
        return pos.copy();
    }
}
