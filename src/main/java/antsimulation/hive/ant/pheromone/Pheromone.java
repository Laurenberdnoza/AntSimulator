package antsimulation.hive.ant.pheromone;

import antsimulation.Main;
import antsimulation.world.Displayable;
import antsimulation.world.Locatable;
import antsimulation.world.Removable;
import antsimulation.world.Updatable;
import org.mini2Dx.gdx.math.Vector2;

import static java.lang.Math.max;

public abstract class Pheromone implements Locatable, Displayable, Updatable, Removable {

    public enum Type {
        FOOD, HOME
    }

    // TODO: Integrate pheromones into the Node class.
    private final Type pheromoneType;

    protected float radius = 8f;
    protected Vector2 pos;
    protected float lifeTime;

    public Pheromone(Vector2 pos, float lifeTime, Type type) {
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
    public Vector2 getLocation() {
        return pos;
    }
}
