package antsimulation.hive.ant;

import antsimulation.Main;
import antsimulation.hive.ant.pheromone.Pheromone;
import antsimulation.world.Displayable;
import antsimulation.world.Locatable;
import antsimulation.world.Updatable;
import antsimulation.world.grid.Node;
import antsimulation.world.objects.food.FoodChunk;
import org.mini2Dx.gdx.math.Vector2;
import processing.core.PConstants;
import processing.core.PImage;

import java.util.Optional;

import static java.lang.Math.max;

// TODO: fix the util.
public class Ant implements Updatable, Displayable, Locatable {

    static final float TURN_SPEED = 180f;

    private static final PImage ANT_TEXTURE = Main.getApp().loadImage("ant.png");

    private static final float PHEROMONE_COOLDOWN = 4f;
    private static final int PHEROMONE_SENSING_RADIUS = 3;

    private final float movementSpeed = 40;
    private final float radius = 6f;

    private final TurningStrategy turningStrategy = new DefaultTurningStrategy(
            this,
            new DefaultFoodCarryingStrategy(this),
            new DefaultWanderingStrategy(this)
    );

    private final Vector2 position;
    private final Vector2 currentDirection = new Vector2().setToRandomDirection().setLength(movementSpeed / Main.getApp().frameRate);
    private Vector2 desiredDirection = new Vector2().setToRandomDirection().setLength(movementSpeed / Main.getApp().frameRate);

    private float timeUntilPheromoneDeposit;

    private FoodChunk carriedFood;

    public Ant(Vector2 startingLocation) {
        this.position = startingLocation.cpy();
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
        desiredDirection = turningStrategy.getDesiredDirection();
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

    private void turn() {
        final float turnDelta = TURN_SPEED / Main.getApp().frameRate;
        final int modifier = (currentDirection.dot(desiredDirection) >= 0) ? 1 : -1;

        currentDirection.rotateDeg(modifier * turnDelta);
    }

    private void move() {
        Vector2 attemptedPos = this.position.cpy().add(currentDirection);

        if (Main.getWorld().inBounds(attemptedPos)) position.add(currentDirection);
        // If obstacle in front, do a 180.
        else {
            position.add(currentDirection.rotateDeg(180));
            desiredDirection = currentDirection.cpy();
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
        carriedFood.setPosition(position.cpy().add(currentDirection.cpy().setLength(radius)));
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
        Main.getApp().rotate(currentDirection.angleRad());

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
    public Vector2 getLocation() {
        return position.cpy();
    }

    boolean carryingFood() {
        return (carriedFood != null);
    }

    int getPheromoneSensingRadius() {
        return PHEROMONE_SENSING_RADIUS;
    }

    Vector2 getDesiredDirection() {
        return desiredDirection.cpy();
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }
}
