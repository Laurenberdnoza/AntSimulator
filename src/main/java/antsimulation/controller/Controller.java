package antsimulation.controller;

import antsimulation.Main;
import antsimulation.world.World;
import antsimulation.world.grid.Node;
import org.mini2Dx.gdx.math.Vector2;

public class Controller {

    private final World world;

    public Controller(World world) {
        this.world = world;
    }

    public void handleMouseDrag() {
        Node targetNode = world.getGrid().getNodeAt(new Vector2(Main.getApp().mouseX, Main.getApp().mouseY));
        targetNode.replenishFood();
    }

    public void handleKeyPress() {
        final char key = Main.getApp().key;

        switch (key) {
            case 'a':
                handleTimeScale();
                break;
            case 's':
                handlePause();
                break;
            default:
                break;
        }
    }

    private void handlePause() {
        Main.getLogicPool().toggleRunning();
    }

    private void handleTimeScale() {
        Main.getLogicPool().toggleTickRate();
    }
}
