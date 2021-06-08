package antsimulation.hive.ant.pheromone;

import antsimulation.Main;
import antsimulation.world.Displayable;
import antsimulation.world.Updatable;
import antsimulation.world.grid.Node;

import static java.lang.Math.max;

public abstract class Pheromone implements Displayable, Updatable {

    public enum Type {
        FOOD, HOME
    }

    private static final float LIFETIME_INCREASE_UPON_REFRESH = 0.5f;

    private final Type pheromoneType;

    protected final Node parent;
    protected float radius;
    protected float maxLifeTime;
    protected float lifeTime = 0;

    public Pheromone(Node parent, float maxLifeTime, Type type) {
        this.parent = parent;
        this.radius = (float) parent.getWidth();
        this.maxLifeTime = maxLifeTime;
        this.pheromoneType = type;
    }

    @Override
    public void update(float dt) {
        lifeTime = max(0, lifeTime - dt);
        onUpdate();
    }

    @Override
    public void display() {
        if (lifeTime > 0 && Main.getSettingsHandler().isPheromonesVisible()) onDisplay();
    }

    protected abstract void onUpdate();

    protected abstract void onDisplay();

    public Type getPheromoneType() {
        return pheromoneType;
    }

    public float getStrength() {
        return lifeTime;
    }

    public void refresh() {
        lifeTime = Math.min(maxLifeTime, lifeTime + (maxLifeTime * LIFETIME_INCREASE_UPON_REFRESH));
    }

    public void mask() {
        lifeTime = 0;
    }

    public Pheromone.Type getType() {
        return pheromoneType;
    }
}
