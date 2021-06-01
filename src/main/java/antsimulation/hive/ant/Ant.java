package antsimulation.hive.ant;

import antsimulation.Main;
import antsimulation.hive.ant.pheromone.FoodPheromone;
import antsimulation.hive.ant.pheromone.HomePheromone;
import antsimulation.hive.ant.pheromone.Pheromone;
import antsimulation.util.MockRectangleObject;
import antsimulation.world.Displayable;
import antsimulation.world.Locatable;
import antsimulation.world.Updatable;
import antsimulation.world.objects.food.Food;
import com.github.ryanp102694.geometry.RectangleObject;
import processing.core.PVector;

import static java.lang.Math.max;

@SuppressWarnings("SuspiciousNameCombination")
public class Ant implements Updatable, Displayable, Locatable, RectangleObject {

    private static final float TURN_AMOUNT = 20f;
    private static final float PHEROMONE_COOLDOWN = 4f;
    private static final float ANTENNA_LENGTH = 2f;
    private static final float ANTENNA_ANGLE = 30f;
    private static final float ANTENNA_PERCEPTION_WIDTH = 2f;
    private static final float ATTRACTION_STRENGTH = 5f;

    private final float movementSpeed = 35f;

    private final PVector pos;
    private final PVector movement = PVector.random2D().setMag(movementSpeed / Main.getApp().frameRate);
    private float radius = 2f;
    private float timeUntilPheromoneDeposit;

    private String id;
    private String type;

    private Food carriedFood;

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
        if (carriedFood != null) beAttractedTo(Pheromone.Type.HOME);
        else beAttractedTo(Pheromone.Type.FOOD);

        // Apply some randomness too.
        movement.rotate(Main.getApp().random(-TURN_AMOUNT / Main.getApp().frameRate, TURN_AMOUNT / Main.getApp().frameRate));
    }

    private void beAttractedTo(Pheromone.Type pheromoneType) {
        PVector forward = new PVector(movement.x, movement.y).setMag(movement.mag() + ANTENNA_LENGTH);
        PVector left = forward.copy().rotate(ANTENNA_ANGLE);
        PVector right = forward.copy().rotate(-ANTENNA_ANGLE);

        MockRectangleObject leftQuery = new MockRectangleObject(left.x, left.y, ANTENNA_PERCEPTION_WIDTH, ANTENNA_PERCEPTION_WIDTH);
        MockRectangleObject rightQuery = new MockRectangleObject(right.x, right.y, ANTENNA_PERCEPTION_WIDTH, ANTENNA_PERCEPTION_WIDTH);

        float leftAttraction = probeArea(leftQuery, pheromoneType);
        float rightAttraction = probeArea(rightQuery, pheromoneType);

        movement.rotate(leftAttraction - rightAttraction);
    }

    private float probeArea(MockRectangleObject queryArea, Pheromone.Type pheromoneType) {
        float total = 0;

        for (RectangleObject obj : Main.getWorld().getPheromones().search(queryArea)) {
            if (((Pheromone) obj).getPheromoneType() == pheromoneType) {
                total += ATTRACTION_STRENGTH;
            }
        }

        return total;
    }

    private void move() {
        PVector attemptedPos = this.pos.copy().add(movement);

        if (Main.getWorld().inBounds(attemptedPos)) pos.add(movement);
        else pos.add(movement.rotate(180));
    }

    private void checkForFood() {
        for (RectangleObject obj : Main.getWorld().getFood().search(this)) {
            Food food = (Food) obj;
            if (!food.isCarried()) {
                takeFood(food);
                break;
            }
        }
    }

    private void takeFood(Food food) {
        food.setCarried(true);
        carriedFood = food;
        carryFood();
    }

    private void carryFood() {
        carriedFood.setPosition(pos.copy().add(movement.copy().setMag(radius)));
    }

    @Override
    public void display() {
        Main.getApp().noStroke();
        Main.getApp().fill(5, 5, 5);
        Main.getApp().circle(pos.x, pos.y, 2 * radius);
    }

    @Override
    public PVector getLocation() {
        return pos;
    }
}
