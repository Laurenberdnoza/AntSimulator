package antsimulation.hive.ant;

import antsimulation.Main;
import antsimulation.world.Displayable;
import antsimulation.world.Locatable;
import antsimulation.world.Updatable;
import antsimulation.world.food.Food;
import processing.core.PVector;

public class Ant implements Updatable, Displayable, Locatable {

    private static final float TURN_AMOUNT = 20f;
    private static final float BODY_RADIUS = 2f;

    private final float movementSpeed = 35f;

    private final PVector pos;
    private final PVector movement = PVector.random2D().setMag(movementSpeed / Main.getApp().frameRate);

    private Food carriedFood;

    public Ant(PVector startingLocation) {
        this.pos = startingLocation.copy();
    }

    @Override
    public void update() {
        if (carriedFood != null) carryFood();
        else checkForFood();
        turn();
        move();
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
        for (Food food : Main.getWorld().getFood()) {
            if (!(food.isCarried()) && PVector.dist(pos, food.getLocation()) <= BODY_RADIUS) {
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
        carriedFood.setPosition(pos.copy().add(movement.copy().setMag(BODY_RADIUS)));
    }

    @Override
    public void display() {
        Main.getApp().noStroke();
        Main.getApp().fill(5, 5, 5);
        Main.getApp().circle(pos.x, pos.y, 2 * BODY_RADIUS);
    }

    @Override
    public PVector getLocation() {
        return pos;
    }
}
