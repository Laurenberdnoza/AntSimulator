package antsimulation.world.grid;

import antsimulation.world.Displayable;
import antsimulation.world.Locatable;
import antsimulation.world.Updatable;
import antsimulation.world.objects.food.FoodChunk;
import antsimulation.world.objects.food.FoodSource;
import processing.core.PVector;

import java.util.Optional;

public class Node implements Updatable, Displayable, Locatable {

    private final int xIndex;
    private final int yIndex;
    private final double width;
    private final double height;
    private final PVector position;
    private final FoodSource foodSource = new FoodSource(this);

    Node(int xIndex, int yIndex, double width, double height) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.width = width;
        this.height = height;
        this.position = new PVector((float) (this.xIndex * width), (float) (this.yIndex * height));
    }

    @Override
    public void display() {
        foodSource.display();
    }

    @Override
    public void update() {
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
    public PVector getLocation() {
        return position;
    }

    public int getYIndex() {
        return yIndex;
    }

    public int getXIndex() {
        return xIndex;
    }
}
