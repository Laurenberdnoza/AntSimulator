package antsimulation.hive.ant;

import antsimulation.Main;
import antsimulation.hive.ant.pheromone.FoodPheromone;
import antsimulation.hive.ant.pheromone.HomePheromone;
import antsimulation.hive.ant.pheromone.Pheromone;
import antsimulation.util.MockRectangleObject;
import antsimulation.world.Displayable;
import antsimulation.world.Locatable;
import antsimulation.world.Updatable;
import antsimulation.world.food.Food;
import com.github.ryanp102694.geometry.RectangleObject;
import processing.core.PVector;

import static java.lang.Math.max;

public class Ant implements Updatable, Displayable, Locatable, RectangleObject {

    private static final float TURN_AMOUNT = 20f;
    private static final float PHEROMONE_COOLDOWN = 2f;
    private static final float PHEROMONE_DETECTION_SIZE = 10f;
    private static final float ATTRACTION_MAGNITUDE = 2f;

    private final float movementSpeed = 35f;

    private final PVector pos;
    private final PVector movement = PVector.random2D().setMag(movementSpeed / Main.getApp().frameRate);
    private final PVector attraction = new PVector(0f, 0f);
    private float radius = 2f;
    private float timeUntilPheromoneDeposit = PHEROMONE_COOLDOWN;

    private String id;
    private String type;

    private Food carriedFood;

    public Ant(PVector startingLocation) {
        this.pos = startingLocation.copy();
    }

    @Override
    public void update() {
        if (carriedFood != null) {
            carryFood();
            depositPheromone(new FoodPheromone(this.pos));
        } else {
            checkForFood();
            depositPheromone(new HomePheromone(this.pos));
        }
        turn();
        move();
        timeUntilPheromoneDeposit = max(0, timeUntilPheromoneDeposit - (1 / Main.getApp().frameRate));
    }

    private void depositPheromone(Pheromone pheromone) {
        if (timeUntilPheromoneDeposit == 0) {
            timeUntilPheromoneDeposit = PHEROMONE_COOLDOWN;
            Main.getWorld().addEntity(pheromone);
        }
    }

    private void turn() {
        movement.rotate(Main.getApp().random(-TURN_AMOUNT / Main.getApp().frameRate, TURN_AMOUNT / Main.getApp().frameRate));
    }

    private void move() {
        PVector attemptedPos = this.pos.copy().add(movement);

        if (Main.getWorld().inBounds(attemptedPos)) pos.add(movement);
        else pos.add(movement.rotate(180));
    }

    private void checkForFood() {
        for (RectangleObject obj : Main.getWorld().getQuadTree().search(this)) {
            if (obj instanceof Food && !(((Food) obj).isCarried())) {
                takeFood((Food) obj);
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


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String s) {
        id = s;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String s) {
        type = s;
    }

    @Override
    public Double getX() {
        return (double) pos.x;
    }

    @Override
    public void setX(Double aDouble) {
        pos.x = aDouble.floatValue();
    }

    @Override
    public Double getY() {
        return (double) pos.y;
    }

    @Override
    public void setY(Double aDouble) {
        pos.y = aDouble.floatValue();
    }

    @Override
    public Double getH() {
        return (double) radius;
    }

    @Override
    public void setH(Double aDouble) {
        radius = aDouble.floatValue();
    }

    @Override
    public Double getW() {
        return (double) radius;
    }

    @Override
    public void setW(Double aDouble) {
        radius = aDouble.floatValue();
    }
}
