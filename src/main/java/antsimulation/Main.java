package antsimulation;

import antsimulation.controller.Controller;
import antsimulation.logicthread.LogicThread;
import antsimulation.world.World;
import processing.core.PApplet;

public class Main extends PApplet {

    private static PApplet app;
    private static final World world = new World();

    private final Controller controller = new Controller(world);

    public static void main(String[] args) {
        PApplet.main("antsimulation.Main");
    }

    public void setup() {
        app = this;
        world.getSpawner().spawnHive(5000);

        LogicThread logicThread = new LogicThread(world, 60);
        logicThread.start();
    }

    public void settings() {
        size(world.getWidth(), world.getHeight(), processing.core.PConstants.P2D);
    }

    public void draw() {
        world.display();
        displayFrameRate();
    }

    private void displayFrameRate() {
        fill(255);
        text(frameRate, 20, 20);
    }

    public void mouseDragged() {
       controller.handleMouseDrag();
    }

    public static PApplet getApp() {
        return app;
    }

    public static World getWorld() {
        return world;
    }
}
