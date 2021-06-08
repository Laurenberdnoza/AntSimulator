package antsimulation;

import antsimulation.controller.Controller;
import antsimulation.threading.ThreadPool;
import antsimulation.world.World;
import processing.core.PApplet;

public class Main extends PApplet {

    private static PApplet app;
    private static final int TICK_RATE_IN_HZ = 60;
    private static final World WORLD = new World();
    private static final ThreadPool LOGIC_POOL = new ThreadPool(WORLD, TICK_RATE_IN_HZ);
    private static final Controller CONTROLLER = new Controller(WORLD);


    public static void main(String[] args) {
        PApplet.main("antsimulation.Main");
    }

    public void setup() {
        app = this;
        WORLD.getSpawner().spawnHive(5000);
        LOGIC_POOL.start();
    }

    public void settings() {
        size(WORLD.getWidth(), WORLD.getHeight(), processing.core.PConstants.P2D);
    }

    public void draw() {
        WORLD.display();
        displayFrameRate();
    }

    private void displayFrameRate() {
        fill(255);
        text(frameRate, 20, 20);
    }

    public void mouseDragged() {
       CONTROLLER.handleMouseDrag();
    }

    public void keyPressed() {
        CONTROLLER.handleKeyPress();
    }

    public static PApplet getApp() {
        return app;
    }

    public static World getWorld() {
        return WORLD;
    }

    public static ThreadPool getLogicPool() {
        return LOGIC_POOL;
    }
}
