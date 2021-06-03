package antsimulation.hive.ant;

import antsimulation.Main;
import antsimulation.hive.ant.pheromone.FoodPheromone;
import antsimulation.hive.ant.pheromone.HomePheromone;
import antsimulation.hive.ant.pheromone.Pheromone;
import antsimulation.world.Displayable;
import antsimulation.world.Locatable;
import antsimulation.world.Updatable;
import antsimulation.world.grid.Node;
import antsimulation.world.objects.food.FoodChunk;
import processing.core.PVector;

import java.util.Optional;

import static java.lang.Math.max;

public class Ant implements Updatable, Displayable, Locatable {

    private static final float TURN_AMOUNT = 20f;
    private static final float PHEROMONE_COOLDOWN = 4f;

    private final float movementSpeed = 35f;

    private final PVector pos;
    private final PVector movement = PVector.random2D().setMag(movementSpeed / Main.getApp().frameRate);
    private final float radius = 2f;
    private float timeUntilPheromoneDeposit;

    private FoodChunk carriedFood;

    public Ant(PVector startingLocation) {
        this.pos = startingLocation.copy();
        this.timeUntilPheromoneDeposit = getPheromoneCooldown();
    }

    @Override
    public void update() {
        if (carriedFood != null) {
            carryFood();
            attemptToDepositPheromone(new FoodPheromone(this.pos));
        } else {
            checkForFood();
            attemptToDepositPheromone(new HomePheromone(this.pos));
        }
        turn();
        move();
        regenerate();
    }

    private void regenerate() {
        timeUntilPheromoneDeposit = max(0, timeUntilPheromoneDeposit - (1 / Main.getApp().frameRate));
    }

    private void attemptToDepositPheromone(Pheromone pheromone) {
        if (timeUntilPheromoneDeposit == 0) {
            timeUntilPheromoneDeposit = getPheromoneCooldown();
            Main.getWorld().addEntity(pheromone);
        }
    }

    private float getPheromoneCooldown() {
        final float varianceRangeStart = 0.5f;
        final float varianceRangeEnd = 1.5f;
        final float randomCoolDownFactor = Main.getApp().random(varianceRangeStart, varianceRangeEnd);
        return randomCoolDownFactor * PHEROMONE_COOLDOWN;
    }

    private void turn() {
        // Apply some randomness too.
        movement.rotate(Main.getApp().random(-TURN_AMOUNT / Main.getApp().frameRate, TURN_AMOUNT / Main.getApp().frameRate));
    }

    private void move() {
        PVector attemptedPos = this.pos.copy().add(movement);

        if (Main.getWorld().inBounds(attemptedPos)) pos.add(movement);
        else pos.add(movement.rotate(180));
    }

    private void checkForFood() {
        Optional<FoodChunk> foodChunk = getNode().giveFood();
        foodChunk.ifPresent(this::takeFood);
    }

    private void takeFood(FoodChunk foodChunk) {
        carriedFood = foodChunk;
        carryFood();
    }

    private void carryFood() {
        carriedFood.setPosition(pos.copy().add(movement.copy().setMag(radius)));
    }

    private Node getNode() {
        return Main.getWorld().getGrid().getNodeAt(getLocation());
    }

    @Override
    public void display() {
        if (carriedFood != null) carriedFood.display();
        Main.getApp().noStroke();
        Main.getApp().fill(5, 5, 5);
        Main.getApp().circle(pos.x, pos.y, 2 * radius);
    }

    @Override
    public PVector getLocation() {
        return pos;
    }
}
