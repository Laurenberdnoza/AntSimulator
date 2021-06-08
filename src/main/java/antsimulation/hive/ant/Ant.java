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

public class Ant implements Updatable, Displayable, Locatable {

    static final float TURN_SPEED = 180f;

    private static final PImage ANT_TEXTURE = Main.getApp().loadImage("ant.png");
    private static final PImage ANT_CARRYING_FOOD_TEXTURE = Main.getApp().loadImage("ant_carrying_food.png");

    private static final float PHEROMONE_COOLDOWN = 3f;
    private static final float RADIUS = 6f;
    private static final int PHEROMONE_SENSING_RADIUS = 7;

    private final float movementSpeed = 60;

    private final TurningStrategy turningStrategy = new DefaultTurningStrategy(
            this,
            new DefaultFoodCarryingStrategy(this),
            new DefaultWanderingStrategy(this)
    );

    private Vector2 position;
    private final Vector2 currentDirection = new Vector2().setToRandomDirection().setLength(movementSpeed);
    private Vector2 desiredDirection = new Vector2().setToRandomDirection().setLength(movementSpeed);

    private float timeUntilPheromoneDeposit;

    private FoodChunk carriedFood;

    public Ant(Vector2 startingLocation) {
        this.position = startingLocation.cpy();
        this.timeUntilPheromoneDeposit = varyCooldown(PHEROMONE_COOLDOWN);
    }

    @Override
    public void update(float dt) {
        if (carryingFood()) {
            attemptToDepositPheromone(Pheromone.Type.FOOD);
        } else {
            checkForFood();
            attemptToDepositPheromone(Pheromone.Type.HOME);
        }
        desiredDirection = turningStrategy.getDesiredDirection(dt);
        turn(dt);
        move(dt);
        reduceCooldowns(dt);
    }

    private void reduceCooldowns(float dt) {
        timeUntilPheromoneDeposit = max(0, timeUntilPheromoneDeposit - dt);
    }

    private void attemptToDepositPheromone(Pheromone.Type pheromoneType) {
        if (timeUntilPheromoneDeposit == 0) {
            getNode().depositPheromone(pheromoneType);
            timeUntilPheromoneDeposit = varyCooldown(PHEROMONE_COOLDOWN);
        }
    }

    private float varyCooldown(float initialCooldown) {
        final float rangeStartFactor = 0.75f;
        final float rangeEndFactor = 1.25f;

        final float randomCoolDownFactor = Main.getApp().random(rangeStartFactor, rangeEndFactor);
        return randomCoolDownFactor * initialCooldown;
    }

    private void turn(float dt) {
        final float turnDelta = TURN_SPEED * dt;
        final int modifier = (currentDirection.dot(desiredDirection) >= 0) ? 1 : -1;

        currentDirection.rotateDeg(modifier * turnDelta);
    }

    private void move(float dt) {
        Vector2 attemptedPos = this.position.cpy().add(currentDirection.cpy().setLength(currentDirection.len() * dt));

        if (Main.getWorld().inBounds(attemptedPos)) position = attemptedPos;
        // If obstacle in front, do a 180.
        else {
            position.add(currentDirection.cpy().setLength(currentDirection.len() * dt).rotateDeg(180));
            desiredDirection = currentDirection.cpy();
        }
    }

    private void checkForFood() {
        Optional<FoodChunk> foodChunk = getNode().giveFood();
        foodChunk.ifPresent(this::takeFood);
    }

    private void takeFood(FoodChunk foodChunk) {
        carriedFood = foodChunk;
    }

    Node getNode() {
        return Main.getWorld().getGrid().getNodeAt(getLocation());
    }

    @Override
    public void display() {
        drawAnt();
    }

    private void drawAnt() {
        Main.getApp().noStroke();
        Main.getApp().pushMatrix();

        Main.getApp().translate(position.x, position.y);
        Main.getApp().rotate(currentDirection.angleRad());

        Main.getApp().beginShape();
        if (carryingFood()) {
            Main.getApp().texture(ANT_CARRYING_FOOD_TEXTURE);
        } else {
            Main.getApp().texture(ANT_TEXTURE);
        }
        Main.getApp().vertex(-RADIUS, -RADIUS, 0, 0);
        Main.getApp().vertex(+RADIUS, -RADIUS, ANT_TEXTURE.width, 0);
        Main.getApp().vertex(+RADIUS, +RADIUS, ANT_TEXTURE.width, ANT_TEXTURE.height);
        Main.getApp().vertex(-RADIUS, +RADIUS, 0, ANT_TEXTURE.height);
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
