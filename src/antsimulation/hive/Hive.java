package antsimulation.hive;

import antsimulation.Main;
import antsimulation.hive.ant.Ant;
import antsimulation.world.Displayable;
import antsimulation.world.Locatable;
import processing.core.PVector;

import java.util.HashSet;
import java.util.Set;

public class Hive implements Displayable, Locatable {

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
        Ant newAnt = new Ant(location.copy());
        ownedAnts.add(newAnt);
        Main.getWorld().addEntity(newAnt);
    }

    @Override
    public void display() {
        Main.getApp().fill(20, 10, 2);
        Main.getApp().circle(location.x, location.y, WIDTH);
    }

    @Override
    public PVector getLocation() {
        return null;
    }

}
