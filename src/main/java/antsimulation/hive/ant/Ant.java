package antsimulation.hive.ant;

import antsimulation.Main;
import antsimulation.hive.ant.pheromone.Pheromone;
import antsimulation.world.Displayable;
import antsimulation.world.Locatable;
import antsimulation.world.Updatable;
import antsimulation.world.grid.Node;
import antsimulation.world.objects.food.FoodChunk;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

import java.util.Optional;

import static java.lang.Math.max;

public class Ant implements Updatable, Displayable, Locatable {

    static final float TURN_AMOUNT = 10f;

    private static final PImage ANT_TEXTURE = Main.getApp().loadImage("ant.png");

    private static final float PHEROMONE_COOLDOWN = 4f;

    private final float movementSpeed = 35f;
    private final float radius = 6f;
    private float timeUntilPheromoneDeposit;

    private final WanderingStrategy wanderingStrategy = new DefaultWanderingStrategy(this);
    private final FoodCarryingStrategy defaultFoodCarryingStrategy = new DefaultFoodCarryingStrategy(this);

    private final PVector position;
    private final PVector desiredDirection = PVector.random2D().setMag(movementSpeed / Main.getApp().frameRate);

    private FoodChunk carriedFood;

    public Ant(PVector startingLocation) {
        this.position = startingLocation.copy();
        this.timeUntilPheromoneDeposit = getPheromoneCooldown();
    }

    @Override
    public void update() {
        if (carryingFood()) {
            carryFood();
            attemptToDepositPheromone(Pheromone.Type.HOME);
        } else {
            checkForFood();
            attemptToDepositPheromone(Pheromone.Type.FOOD);
        }
        turn();
        move();
        reduceCooldowns();
    }

    private void reduceCooldowns() {
        timeUntilPheromoneDeposit = max(0, timeUntilPheromoneDeposit - (1 / Main.getApp().frameRate));
    }

    private void attemptToDepositPheromone(Pheromone.Type pheromoneType) {
        if (timeUntilPheromoneDeposit == 0) {
            timeUntilPheromoneDeposit = getPheromoneCooldown();
        }
    }

    private float getPheromoneCooldown() {
        final float varianceRangeStart = 0.5f;
        final float varianceRangeEnd = 1.5f;
        final float randomCoolDownFactor = Main.getApp().random(varianceRangeStart, varianceRangeEnd);
        return randomCoolDownFactor * PHEROMONE_COOLDOWN;
    }

    private void turn() {
        if (!carryingFood()) desiredDirection.rotate(wanderingStrategy.getRotation());
        else desiredDirection.rotate(defaultFoodCarryingStrategy.getRotation());
    }

    private void move() {
        PVector attemptedPos = this.position.copy().add(desiredDirection);

        if (Main.getWorld().inBounds(attemptedPos)) position.add(desiredDirection);
        else position.add(desiredDirection.rotate(180));
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
        carriedFood.setPosition(position.copy().add(desiredDirection.copy().setMag(radius)));
    }

    Node getNode() {
        return Main.getWorld().getGrid().getNodeAt(getLocation());
    }

    @Override
    public void display() {
        if (carryingFood()) carriedFood.display();
        drawAnt();
    }

    private void drawAnt() {
        Main.getApp().noStroke();
        Main.getApp().pushMatrix();

        Main.getApp().translate(position.x, position.y);
        Main.getApp().rotate(desiredDirection.heading());

        Main.getApp().beginShape();
        Main.getApp().texture(ANT_TEXTURE);
        Main.getApp().vertex(-radius, -radius, 0, 0);
        Main.getApp().vertex(+radius, -radius, ANT_TEXTURE.width, 0);
        Main.getApp().vertex(+radius, +radius, ANT_TEXTURE.width, ANT_TEXTURE.height);
        Main.getApp().vertex(-radius, +radius, 0, ANT_TEXTURE.height);
        Main.getApp().endShape(PConstants.CLOSE);

        Main.getApp().popMatrix();
    }

    @Override
    public PVector getLocation() {
        return position;
    }

    boolean carryingFood() {
        return (carriedFood != null);
    }
}
