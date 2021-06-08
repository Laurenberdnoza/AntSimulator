package antsimulation.hive.ant.pheromone;

import antsimulation.Main;
import antsimulation.world.Displayable;
import antsimulation.world.Locatable;
import antsimulation.world.Updatable;
import antsimulation.world.grid.Node;
import org.mini2Dx.gdx.math.Vector2;

import static java.lang.Math.max;

public abstract class Pheromone implements Locatable, Displayable, Updatable {

    public enum Type {
        FOOD, HOME
    }

    private final Type pheromoneType;

    protected final Node parent;
    protected float maxLifeTime;
    protected float radius = 8f;
    protected float lifeTime = 0;

    public Pheromone(Node parent, float maxLifeTime, Type type) {
        this.parent = parent;
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

    @Override
    public Vector2 getLocation() {
        return parent.getLocation();
    }

    public float getStrength() {
        return lifeTime;
    }

    public void refresh() {
        lifeTime = maxLifeTime;
    }
}
