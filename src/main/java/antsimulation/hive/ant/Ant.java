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
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

import java.util.Optional;

import static java.lang.Math.max;

public class Ant implements Updatable, Displayable, Locatable {

    private static final PImage ANT_TEXTURE = Main.getApp().loadImage("ant.png");

    private static final float TURN_AMOUNT = 20f;
    private static final float PHEROMONE_COOLDOWN = 4f;

    private final float movementSpeed = 35f;
    private final float radius = 4f;
    private float timeUntilPheromoneDeposit;

    private final PVector pos;
    private final PVector movement = PVector.random2D().setMag(movementSpeed / Main.getApp().frameRate);

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
        // Some randomness too.
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
        drawAnt();
    }

    private void drawAnt() {
        Main.getApp().noStroke();
        Main.getApp().pushMatrix();

        Main.getApp().translate(pos.x, pos.y);
        Main.getApp().rotate(movement.heading());

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
        return pos;
    }
}
