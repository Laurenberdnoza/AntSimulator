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

    private final WanderingStrategy wanderingStrategy = new DefaultWanderingStrategy(this);
    private final FoodCarryingStrategy defaultFoodCarryingStrategy = new DefaultFoodCarryingStrategy(this);

    private final PVector position;
    private PVector desiredDirection = PVector.random2D().setMag(movementSpeed / Main.getApp().frameRate);
    private PVector currentDirection = PVector.random2D().setMag(movementSpeed / Main.getApp().frameRate);

    private float timeUntilPheromoneDeposit;
    private FoodChunk carriedFood;

    public Ant(PVector startingLocation) {
        this.position = startingLocation.copy();
        this.timeUntilPheromoneDeposit = varyCooldown(PHEROMONE_COOLDOWN);
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
        getDesiredDirection();
        turn();
        move();
        reduceCooldowns();
    }

    private void reduceCooldowns() {
        timeUntilPheromoneDeposit = max(0, timeUntilPheromoneDeposit - (1 / Main.getApp().frameRate));
    }

    private void attemptToDepositPheromone(Pheromone.Type pheromoneType) {
        if (timeUntilPheromoneDeposit == 0) {
            timeUntilPheromoneDeposit = varyCooldown(PHEROMONE_COOLDOWN);
        }
    }

    private float varyCooldown(float initialCooldown) {
        final float rangeStartFactor = 0.75f;
        final float rangeEndFactor = 1.25f;

        final float randomCoolDownFactor = Main.getApp().random(rangeStartFactor, rangeEndFactor);
        return randomCoolDownFactor * initialCooldown;
    }

    private void getDesiredDirection() {
        if (!carryingFood()) desiredDirection.rotate(wanderingStrategy.getRotation());
        else desiredDirection.rotate(defaultFoodCarryingStrategy.getRotation());
    }

    private void turn() {
        currentDirection = desiredDirection.copy();
    }

    private void move() {
        PVector attemptedPos = this.position.copy().add(currentDirection);

        if (Main.getWorld().inBounds(attemptedPos)) position.add(currentDirection);
        // If obstacle in front, do a 180.
        else {
            position.add(currentDirection.rotate((float) Math.PI));
            desiredDirection = currentDirection.copy();
        }
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
        carriedFood.setPosition(position.copy().add(currentDirection.copy().setMag(radius)));
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
        Main.getApp().rotate(currentDirection.heading());

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

    int getPheromoneSensingRadius() {
        int pheromoneSensingRadius = 3;
        return pheromoneSensingRadius;
    }
}
