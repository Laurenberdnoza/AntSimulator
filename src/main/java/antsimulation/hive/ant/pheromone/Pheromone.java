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

    private final Type pheromoneType;

    protected final Node parent;
    protected float radius;
    protected float maxLifeTime;
    protected float intensity = 0;

    public Pheromone(Node parent, float maxLifeTime, Type type) {
        this.parent = parent;
        this.radius = (float) parent.getWidth();
        this.pheromoneType = type;
    }

    @Override
    public void update(float dt) {
        intensity = max(0, intensity - dt);
        onUpdate();
    }

    @Override
    public void display() {
        if (intensity > 0 && Main.getSettingsHandler().isPheromonesVisible()) onDisplay();
    }

    protected abstract void onUpdate();

    protected abstract void onDisplay();

    public float getStrength() {
        return intensity;
    }

    public void addIntensity(float intensity) {
        this.intensity += intensity;
    }

    public void scaleIntensity(float scale) {
        this.intensity *= scale;
    }
}
