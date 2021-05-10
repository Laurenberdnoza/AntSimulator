package antsimulation.hive.ant.pheromone;

import antsimulation.Main;
import antsimulation.world.Displayable;
import antsimulation.world.Locatable;
import antsimulation.world.Removable;
import antsimulation.world.Updatable;
import com.github.ryanp102694.geometry.RectangleObject;
import processing.core.PVector;

import static java.lang.Math.max;

public abstract class Pheromone implements Locatable, Displayable, Updatable, Removable, RectangleObject {

    private String id;
    private String type = "food";

    protected float radius = 4f;
    protected PVector pos;
    protected float lifeTime;

    public Pheromone(PVector pos, float lifeTime) {
        this.pos = pos;
        this.lifeTime = lifeTime;
    }

    @Override
    public boolean isToBeRemoved() {
        return (lifeTime == 0);
    }

    @Override
    public void update() {
        lifeTime = max(0, lifeTime - (1 / Main.getApp().frameRate));
        onUpdate();
    }

    protected abstract void onUpdate();

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
