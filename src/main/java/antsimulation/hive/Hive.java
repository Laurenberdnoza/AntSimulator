package antsimulation.hive;

import antsimulation.Main;
import antsimulation.hive.ant.Ant;
import antsimulation.world.Displayable;
import processing.core.PVector;

import java.util.HashSet;
import java.util.Set;

public class Hive implements Displayable {

    private static final float WIDTH = 32f;

    private final PVector location;
    private final Set<Ant> ownedAnts = new HashSet<>();

    public Hive(PVector location) {
       this.location = location;
    }

    public Hive(PVector location, int initialAntAmount) {
        this.location = location;
        spawnAnts(initialAntAmount);
    }

    public void spawnAnts(int amount) {
        for (int i = 0; i < amount; i++) spawnAnt();
    }

    private void spawnAnt() {
        Ant newAnt = new Ant(location.copy().add(PVector.random2D().setMag(Main.getApp().random(0, WIDTH))));
        ownedAnts.add(newAnt);
        Main.getWorld().addEntity(newAnt);
    }

    @Override
    public void display() {
        Main.getApp().fill(20, 10, 2);
        Main.getApp().circle(location.x, location.y, WIDTH);
    }
}
