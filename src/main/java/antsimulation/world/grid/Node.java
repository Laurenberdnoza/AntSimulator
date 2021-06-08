package antsimulation.world.grid;

import antsimulation.hive.ant.pheromone.FoodPheromone;
import antsimulation.hive.ant.pheromone.HomePheromone;
import antsimulation.hive.ant.pheromone.Pheromone;
import antsimulation.world.Displayable;
import antsimulation.world.Locatable;
import antsimulation.world.Updatable;
import antsimulation.world.objects.food.FoodChunk;
import antsimulation.world.objects.food.FoodSource;
import org.mini2Dx.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Node implements Updatable, Displayable, Locatable {

    private final int xIndex;
    private final int yIndex;
    private final double width;

    private final double height;
    private final Vector2 position;
    private final FoodSource foodSource;
    private final Map<Pheromone.Type, Pheromone> pheromones = new HashMap<>();

    Node(int xIndex, int yIndex, double width, double height) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.width = width;
        this.height = height;
        this.position = new Vector2((float) ((this.xIndex + 0.5) * width), (float) ((this.yIndex + 0.5f) * height));
        this.foodSource = new FoodSource(this);
        pheromones.put(Pheromone.Type.HOME, new HomePheromone(this));
        pheromones.put(Pheromone.Type.FOOD, new FoodPheromone(this));
    }

    @Override
    public void update(float dt) {
        for (Pheromone pheromone : pheromones.values()) pheromone.update(dt);
    }

    @Override
    public void display() {
        foodSource.display();
        for (Pheromone pheromone : pheromones.values()) pheromone.display();
    }

    public Optional<FoodChunk> giveFood() {
        return foodSource.takeChunk();
    }

    public void replenishFood() {
        foodSource.replenish();
    }

    public double getWidth() {
        return width;
    }

    @Override
    public Vector2 getLocation() {
        return position.cpy();
    }

    public int getYIndex() {
        return yIndex;
    }

    public int getXIndex() {
        return xIndex;
    }

    public float getPheromoneStrength(Pheromone.Type type) {
        return pheromones.get(type).getStrength();
    }

    public void depositPheromone(Pheromone.Type pheromoneType) {
        pheromones.values().stream()
                .filter(pheromone -> !pheromone.getType().equals(pheromoneType))
                .forEach(Pheromone::mask);
        pheromones.get(pheromoneType).refresh();
    }

    public double getHeight() {
        return height;
    }
}
