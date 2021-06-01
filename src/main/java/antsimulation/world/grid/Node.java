package antsimulation.world.grid;

import antsimulation.world.Displayable;
import antsimulation.world.GridEntity;
import antsimulation.world.Removable;
import antsimulation.world.Updatable;
import antsimulation.world.objects.food.Food;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Node implements Updatable, Displayable {

    private final Set<Updatable> updatables = ConcurrentHashMap.newKeySet();
    private final Set<Displayable> displayables = ConcurrentHashMap.newKeySet();

    private final Set<Food> food = new HashSet<>();

    public Set<Food> getFood() {
        return food;
    }

    public void add(GridEntity entity) {
        if (entity instanceof Food) food.add((Food) entity);
        if (entity instanceof Displayable) displayables.add((Displayable) entity);
        if (entity instanceof Updatable) updatables.add((Updatable) entity);
    }

    @Override
    public void display() {
        for (Displayable displayable : displayables) displayable.display();
    }

    @Override
    public void update() {
        for (Updatable updatable : updatables) updatable.update();
        clearUnusedObjects();
    }

    private void clearUnusedObjects() {
        for (Updatable updatable : updatables) {
            if (updatable instanceof Removable && (((Removable) updatable).isToBeRemoved())) {
                updatables.remove(updatable);
                displayables.remove(updatable);
            }
        }
    }

    public Food giveFood() {
        if (food.isEmpty()) throw new RuntimeException("No food in this node to give!");

        Food chosenFood = food.iterator().next();
        food.remove(chosenFood);
        return chosenFood;
    }
}
