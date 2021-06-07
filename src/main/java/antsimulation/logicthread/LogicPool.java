package antsimulation.logicthread;

import antsimulation.world.World;

import java.util.HashSet;
import java.util.Set;

public class LogicPool {

    private final double tickRate;
    private final World world;

    private boolean running = false;
    private Set<LogicThread> logicThreads = new HashSet<>();

    public LogicPool(World world, int tickRateInHz) {
        this.world = world;
        this.tickRate = 1000000000.0 / tickRateInHz;
    }

    public void start() {
        logicThreads = new HashSet<>();
        spawnThreads();
        running = true;
    }

    private void spawnThreads() {
        logicThreads.add(new LogicThread(world, tickRate));
        for (LogicThread logicThread : logicThreads) logicThread.start();
    }

    public void stop() {
        if (!running) throw new IllegalStateException("Can not stop a pool that is not running.");

        for (LogicThread logicThread : logicThreads) logicThread.shutDown();
        running = false;
    }

    public void toggleRunning() {
        if (running) stop();
        else start();
    }
}
