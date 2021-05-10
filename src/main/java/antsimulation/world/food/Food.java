package antsimulation.world.food;

import antsimulation.Main;
import antsimulation.world.Displayable;
import antsimulation.world.Locatable;
import com.github.ryanp102694.geometry.RectangleObject;
import processing.core.PVector;

public class Food implements Displayable, Locatable, RectangleObject {

    private String id;
    private String type = "food";
    private float radius = 2f;

    private PVector pos;
    private boolean carried = false;

    public Food(PVector position) {
        this.pos = position;
    }

    @Override
    public void display() {
        Main.getApp().fill(20, 180, 20);
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

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String s) {
        id = s;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String s) {
        type = s;
    }

    @Override
    public Double getX() {
        return (double) pos.x;
    }

    @Override
    public void setX(Double aDouble) {
        pos.x = aDouble.floatValue();
    }

    @Override
    public Double getY() {
        return (double) pos.y;
    }

    @Override
    public void setY(Double aDouble) {
        pos.y = aDouble.floatValue();
    }

    @Override
    public Double getH() {
        return (double) radius;
    }

    @Override
    public void setH(Double aDouble) {
        radius = aDouble.floatValue();
    }

    @Override
    public Double getW() {
        return (double) radius;
    }

    @Override
    public void setW(Double aDouble) {
        radius = aDouble.floatValue();
    }
}
