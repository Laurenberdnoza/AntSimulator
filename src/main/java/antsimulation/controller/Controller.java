package antsimulation.controller;

import antsimulation.Main;
import antsimulation.world.World;
import antsimulation.world.grid.Node;
import processing.core.PVector;

public class Controller {

    private final World world;

    public Controller(World world) {
        this.world = world;
    }

    public void handleMouseDrag() {
        Node targetNode = world.getGrid().getNodeAt(new PVector(Main.getApp().mouseX, Main.getApp().mouseY));
        targetNode.replenishFood();
    }
}
