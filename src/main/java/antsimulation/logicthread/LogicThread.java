package antsimulation.logicthread;

import antsimulation.world.World;

public class LogicThread extends Thread {

    private final double tickRate;
    private final World world;

    private boolean running = false;

    LogicThread(World world, double tickRate) {
        this.world = world;
        this.tickRate = tickRate;
    }

    @Override
    public void run() {
        running = true;
        double delta = 0;

        long lastTime = System.nanoTime();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / tickRate;
            lastTime = now;

            while (delta >= 1) {
                world.update();
                delta--;
            }
        }
    }

    void shutDown() {
        running = false;
    }
}
