package antsimulation.world.objects.food;

import antsimulation.Main;
import antsimulation.world.Displayable;
import antsimulation.world.Locatable;
import processing.core.PVector;

public class FoodChunk implements Locatable, Displayable {

    private static final float RADIUS = 2f;

    private PVector position;

    public void setPosition(PVector position) {
        this.position = position;
    }

    @Override
    public void display() {
        Main.getApp().noStroke();
        Main.getApp().fill(0, 200, 0);
        Main.getApp().circle(position.x, position.y, 2 * RADIUS);
    }

    @Override
    public PVector getLocation() {
        return position;
    }
}
