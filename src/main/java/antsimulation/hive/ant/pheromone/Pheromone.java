package antsimulation.hive.ant.pheromone;

import antsimulation.Main;
import antsimulation.world.Displayable;
import antsimulation.world.Locatable;
import antsimulation.world.Removable;
import antsimulation.world.Updatable;
import processing.core.PVector;

import static java.lang.Math.max;

public abstract class Pheromone implements Locatable, Displayable, Updatable, Removable {

    public enum Type {
        FOOD, HOME
    }

    private final Type pheromoneType;

    protected float radius = 8f;
    protected PVector pos;
    protected float lifeTime;

    public Pheromone(PVector pos, float lifeTime, Type type) {
        this.pos = pos;
        this.lifeTime = lifeTime;
        this.pheromoneType = type;
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

    public Type getPheromoneType() {
        return pheromoneType;
    }

    @Override
    public PVector getLocation() {
        return pos;
    }
}
