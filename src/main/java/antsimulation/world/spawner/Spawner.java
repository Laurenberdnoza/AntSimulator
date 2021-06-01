package antsimulation.world.spawner;

import antsimulation.Main;
import antsimulation.hive.Hive;
import antsimulation.world.World;
import antsimulation.world.objects.food.Food;
import processing.core.PVector;

public class Spawner {

    private final World world;

    public Spawner(World world) {
        this.world = world;
    }

    public void spawnHive(int amountOfAnts) {
        Hive newHive = new Hive(world.getRandomLocation(), amountOfAnts);
        world.addEntity(newHive);
    }

    public void spawnFoodCluster(int clusterSize) {
        final float clusterRadius = 32f;
        final PVector clusterCenter = world.getRandomLocationAwayFromEdgeBy(clusterRadius);

        for (int i = 0; i < clusterSize; i++) spawnFood(clusterCenter, clusterRadius);
    }

    private void spawnFood(PVector clusterCenter, float clusterRadius) {
       final PVector offset = PVector.random2D().setMag(Main.getApp().random(0, clusterRadius));
       Food newFood = new Food(clusterCenter.copy().add(offset));
       world.addEntity(newFood);
    }
}
