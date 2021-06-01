package antsimulation.world.objects.food;

import antsimulation.Main;
import antsimulation.world.Displayable;
import antsimulation.world.GridEntity;
import antsimulation.world.Locatable;
import processing.core.PVector;

public class Food implements Displayable, Locatable, GridEntity {

    private final float radius = 2f;

    private PVector pos;
    private boolean carried = false;

    public Food(PVector position) {
        this.pos = position;
    }

    @Override
    public void display() {
        Main.getApp().fill(180, 120, 20);
        Main.getApp().circle(pos.x, pos.y, 2 * radius);
    }

    public boolean isCarried() {
        return carried;
    }

    public void setCarried(boolean carried) {
        this.carried = carried;
    }

    public void setPosition(PVector position) {
        this.pos = position;
    }

    @Override
    public PVector getLocation() {
        return pos;
    }
}
