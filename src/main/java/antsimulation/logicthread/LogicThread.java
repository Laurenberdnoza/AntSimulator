package antsimulation.logicthread;

import antsimulation.world.World;

public class LogicThread extends Thread {

    private final double tickRate;
    private final World world;

    private boolean running = false;

    public LogicThread(World world, int tickRateInHz) {
        this.world = world;
        this.tickRate = 1000000000.0 / tickRateInHz;
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

    public void freeze() {
        running = false;
    }

    public void thaw() {
        running = true;
    }
}
