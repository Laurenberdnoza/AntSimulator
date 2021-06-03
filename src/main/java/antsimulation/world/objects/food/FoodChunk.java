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
        Main.getApp().circle(position.x, position.y, RADIUS);
    }

    @Override
    public PVector getLocation() {
        return position;
    }
}
